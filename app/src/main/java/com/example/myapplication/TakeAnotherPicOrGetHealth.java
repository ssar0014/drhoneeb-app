package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import com.amplifyframework.core.Amplify;
import com.example.myapplication.Globals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//This class is for taking user confirmation to get the bee health or to choose another photo.
public class TakeAnotherPicOrGetHealth extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    Button beeHealth;
    Button gotoCamera;
    ImageView bee;
    private final int cameraRequest = 1;
    private int selectFile;
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/image";
    Uri imageUri;
    

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
        bee = (ImageView) findViewById(R.id.anotherPicture);
        //Getting image from global variables.
        Globals g = Globals.getInstance();
        byte[] picture = g.getData();
        Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        imageUri = getImageUri(getApplicationContext(),bmp);
        bee.setImageURI(imageUri);
        //When clicking on "Get bee health" button.
        beeHealth.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                    uploadFile();
                    downloadFile();
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
                    imageUri = getImageUri(getApplicationContext(),bitmap);
                    bee.setImageURI(imageUri);
//                    bee.setImageBitmap(bitmap);
                } catch (IOException var6) {
                    var6.printStackTrace();
                    Toast.makeText((TakeAnotherPicOrGetHealth.this), (CharSequence)"Failed", (int) 0).show();
                }
            }
        } else if (requestCode == this.CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageUri = getImageUri(getApplicationContext(),thumbnail);
//            bee.setImageBitmap(thumbnail);
            bee.setImageURI(imageUri);
            this.saveImage(thumbnail);
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
    private void uploadFile() {
        Toast.makeText(this,"Please wait while we get your bee health condition!", Toast.LENGTH_LONG).show();
        File sampleFile = new File(getApplicationContext().getFilesDir(), "sample.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(sampleFile));
            writer.append("Hello World!");
            writer.close();
        }
        catch(Exception exception) {
            Log.e("StorageQuickstart", exception.getMessage(), exception);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bee);
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir,"history" + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        Amplify.Storage.uploadFile(
                "myUploadedFile.png",
                imageFile.getAbsolutePath(),
                result -> Log.i("StorageQuickStart", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("StorageQuickstart", "Upload error.", storageFailure)
        );
        try {
            Log.i("Timer", "Timer activated");
            //set time in mili
            Thread.sleep(10000);
            Log.i("Timer", "Timer activated");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void downloadFile() {
        Amplify.Storage.downloadFile(
                "result.json",
                getApplicationContext().getFilesDir() + "/download.json",
                result -> Log.i("StorageQuickStart", "Successfully downloaded: " + result.getFile().getName()),
                storageFailure -> Log.e("StorageQuickStart", storageFailure.getMessage(),storageFailure)
        );
        String health = null;
        String description = null;
        String species = null;
        File file = new File(getApplicationContext().getFilesDir() + "/download.json");
        try {
            Log.i("Timer", "Timer activated");
            //set time in mili
            Thread.sleep(5000);
            Log.i("Timer", "Timer activated");

        }catch (Exception e){
            e.printStackTrace();
        }
        /*Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String i = input.nextLine();
                Log.i("data", i );
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();// This responce will have Json Format String
            String result = stringBuilder.toString();
            result = result.replaceAll("\\\\","");
            int length = result.length();
            String newString = result.substring(1,length-1);
            Log.i("result", result);

            JSONObject obj = new JSONObject(newString);
            JSONArray jArray = obj.getJSONArray("response");
            JSONObject j = jArray.getJSONObject(0);
            health = j.getString("status");
            description = j.getString("problem");
            species = j.getString("species");
            Log.i("bee health", health);
            Log.i("bee health description", description);
            Log.i("bee species", species);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent_upload = new Intent(TakeAnotherPicOrGetHealth.this, ResultActivity.class);
        intent_upload.putExtra("bee health condition", health);
        intent_upload.putExtra("bee health description", description);
        intent_upload.putExtra("bee species", species);
        startActivity(intent_upload);
    }
}

