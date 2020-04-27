package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import com.example.myapplication.Globals;

//This class is for taking user confirmation to get the bee health or to choose another photo.
public class TakeAnotherPicOrGetHealth extends AppCompatActivity {

    Button beeHealth;
    Button gotoCamera;
    ImageView bee;
    private final int cameraRequest = 1;
    private int selectFile;
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/image";
    

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.App);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotherphoto_layout);
        //Initializing "Get bee health" button.
        beeHealth = (Button) findViewById(R.id.button_beeHealth);
        //Initializing "Take another Photo" button.
        gotoCamera = (Button) findViewById(R.id.button_takeAnotherPic);
        //Getting image from global variables.
        Globals g = Globals.getInstance();
        byte[] picture = g.getData();
        Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        bee = (ImageView) findViewById(R.id.anotherPicture);
        bee.setImageBitmap(bmp);
        //When clicking on "Get bee health" button.
        beeHealth.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                    Intent intent_upload = new Intent(TakeAnotherPicOrGetHealth.this, ResultActivity.class);
                    startActivity(intent_upload);
                }
            }));
        //When clicking on "Take another Photo" button.
        gotoCamera.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                showPictureDialog();
            }
        }));
        };

    private final void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder((TakeAnotherPicOrGetHealth.this));
        pictureDialog.setTitle((CharSequence)"Select Action");
        String[] pictureDialogItems = new String[]{"Select image from gallery", "Capture photo from camera"};
        pictureDialog.setItems((CharSequence[])pictureDialogItems, (android.content.DialogInterface.OnClickListener)(new android.content.DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        chooseImageFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                }

            }
        }));
        pictureDialog.show();
    }
    //When users choose gallery.
    public final void chooseImageFromGallery() {
        Intent galleryIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, this.GALLERY);
    }
    //When users choose camera.
    private final void takePhotoFromCamera() {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(cameraIntent, this.CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(TakeAnotherPicOrGetHealth.this.getContentResolver(), contentURI);
                    this.saveImage(bitmap);
                    //Toast.makeText((getContext()), (CharSequence)"Image Show!", (int) 0).show();
                    bee.setImageBitmap(bitmap);
                } catch (IOException var6) {
                    var6.printStackTrace();
                    Toast.makeText((TakeAnotherPicOrGetHealth.this), (CharSequence)"Failed", (int) 0).show();
                }
            }
        } else if (requestCode == this.CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            bee.setImageBitmap(thumbnail);
            this.saveImage(thumbnail);
        }

    }

    //Saving image in gallery.
    public final String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().toString() + "/images");
        Log.d("fee", wallpaperDirectory.toString());
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            Log.d("heel", wallpaperDirectory.toString());
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile((TakeAnotherPicOrGetHealth.this), new String[]{f.getPath()}, new String[]{"image/png"}, (MediaScannerConnection.OnScanCompletedListener)null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();

        } catch (IOException error) {
            error.printStackTrace();
            return "";
        }
    }

}

