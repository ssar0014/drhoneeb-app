package com.example.myapplication.ui.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.provider.MediaStore.Images.Media;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import com.example.myapplication.R;
import com.example.myapplication.TakeAnotherPicOrGetHealth;
import com.example.myapplication.Globals;


//This class is opened when user starts the application.
// Users can take a photo by selecting an image from the gallery or by clicking a photo from the camera
public class cameraFragment extends Fragment {
    private cameraViewModel cameraViewModel;
    private Button takePic;
    private final int cameraRequest = 1;
    private int selectFile;
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final String IMAGE_DIRECTORY = "/image";
    byte[] sample = null;
    private String[] storagePermissions;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cameraViewModel = ViewModelProviders.of(this).get(cameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        //Initializing "Take A Photo" button.
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        takePic = (Button) root.findViewById(R.id.button_takePic);
        //When clicking on "Take A Photo" button.
        takePic.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                }
                showPictureDialog();
            }
        }));

        return root;
    }
    //Method for taking image from gallery or clicking directly from camera.
    private final void showPictureDialog() {
        Builder pictureDialog = new Builder((getContext()));
        //Giving users an option to choose from gallery or camera.
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
    //Intent if users choose gallery.
    public final void chooseImageFromGallery() {
        Intent galleryIntent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, this.GALLERY);
    }
    //Intent if users choose camera.
    private final void takePhotoFromCamera() {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(cameraIntent, this.CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        byte[] byteArray;
        ByteArrayOutputStream stream;
        Bitmap bitmap = null;
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();

                    try {
                        //Saving image from gallery.
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                        this.saveImage(bitmap);
                    } catch (IOException var6) {
                        var6.printStackTrace();
                        Toast.makeText((getContext()), (CharSequence)"Failed", (int) 0).show();
                    }
                }
            } else if (requestCode == this.CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");
                this.saveImage(bitmap);
                //Toast.makeText((getContext()), (CharSequence)"Photo Show!",  (int) 0).show();

            }
            //Saving image as bytestream.
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            sample = stream.toByteArray();
            //Saving image in a global variable.
            Globals g = Globals.getInstance();
            g.setData(sample);
            //Calling TakeAnotherPicOrGetHealth class.
            Intent askUserToTakeAnotherPicIntent = new Intent(getContext(), TakeAnotherPicOrGetHealth.class);
            startActivity(askUserToTakeAnotherPicIntent);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Saving image in gallery.
    public final String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(CompressFormat.PNG, 90, (OutputStream)bytes);
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
            MediaScannerConnection.scanFile((getContext()), new String[]{f.getPath()}, new String[]{"image/png"}, (OnScanCompletedListener)null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();

        } catch (IOException error) {
            error.printStackTrace();
            return "";
        }
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(),storagePermissions, STORAGE_REQUEST_CODE);
    }
}


