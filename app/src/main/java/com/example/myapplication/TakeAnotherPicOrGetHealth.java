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
import android.os.AsyncTask;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    boolean flag = false;
    Button beeHealth;
    Button gotoCamera;
    ImageView bee;
    Bitmap bm;
    byte[] sample = null;
    private final int cameraRequest = 1;
    private int selectFile;
    private final int GALLERY = 1;
    private final int CAMERA = 2;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermission;
    private String[] storagePermissions;

    private static final String IMAGE_DIRECTORY = "/image";
    Uri imageUri;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.App);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotherphoto_layout);

        cameraPermission = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //Initializing "Get bee health" button.
        beeHealth = (Button) findViewById(R.id.button_beeHealth);
        //Initializing "Take another Photo" button.
        gotoCamera = (Button) findViewById(R.id.button_takeAnotherPic);
        bee = (ImageView) findViewById(R.id.anotherPicture);
        //Getting image from global variables.
        Globals g = Globals.getInstance();
        byte[] picture = g.getData();
        bm = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        imageUri = getImageUri(getApplicationContext(),bm);
        bee.setImageURI(imageUri);
        //When clicking on "Get bee health" button.
        beeHealth.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Toast.makeText(getApplicationContext(),"Picture is uploading, please wait.....", Toast.LENGTH_LONG).show();
                uploadAsyncTask uploadImage = new uploadAsyncTask();
                uploadImage.execute();
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
                        if (!checkStoragePermission()) {
                            requestStoragePermission();
                        }
                        else {
                            chooseImageFromGallery();
                        }

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
        ByteArrayOutputStream stream;
        if (resultCode == RESULT_OK) {
            if (requestCode == this.GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();

                    try {
                        bm = MediaStore.Images.Media.getBitmap(TakeAnotherPicOrGetHealth.this.getContentResolver(), contentURI);
                        this.saveImage(bm);
                        //Toast.makeText((getContext()), (CharSequence)"Image Show!", (int) 0).show();
                        imageUri = getImageUri(getApplicationContext(),bm);
                        bee.setImageURI(imageUri);
//                    bee.setImageBitmap(bitmap);
                    } catch (IOException var6) {
                        var6.printStackTrace();
                        Toast.makeText((TakeAnotherPicOrGetHealth.this), (CharSequence)"Failed", (int) 0).show();
                    }
                }
            } else if (requestCode == this.CAMERA) {
                bm = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(getApplicationContext(),bm);
                bee.setImageURI(imageUri);
                this.saveImage(bm);
            }
        }
        stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        sample = stream.toByteArray();
        //Saving image in a global variable.
        Globals g = Globals.getInstance();
        g.setData(sample);

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

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission, CAMERA_REQUEST_CODE);
    }
    private class uploadAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String output = null;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.incorrect);
            File filesDir = getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir,"history" + ".png");
            //bm.describeContents();

            try {
                OutputStream os = new FileOutputStream(imageFile);
                bm.compress(Bitmap.CompressFormat.PNG, 100, os);

                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }
            Amplify.Storage.uploadFile(
                    "user_photo.png",
                    imageFile.getAbsolutePath(),
                    result -> Log.i("StorageQuickStart", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("StorageQuickstart", "Upload error.", storageFailure)
            );
            return output;
        }
        @Override
        protected void onPostExecute(String response) {
            try {
                Log.i("Timer", "Timer activated");
                //set time in mili
                Thread.sleep(7000);
                Log.i("Timer", "Timer activated");

            }catch (Exception e){
                e.printStackTrace();
            }
            httpGetBeeOrNotBeeAsyncTask getBeeTask = new httpGetBeeOrNotBeeAsyncTask();
            getBeeTask.execute();

        }
    }

    private class httpGetBeeOrNotBeeAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {

            String beeOrNot = null;
            String REQUEST_METHOD = "GET";
            int READ_TIMEOUT = 15000;
            int CONNECTION_TIMEOUT = 15000;
            String stringUrl = "https://drhoneeb-bee-filter.herokuapp.com";
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);         //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();         //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();       //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());         //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();         //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }         //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();         //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

                result = result.replaceAll("\\\\","");
                int length = result.length();
                Log.i("result", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jArray = obj.getJSONArray("response");
                    JSONObject j = jArray.getJSONObject(0);
                    beeOrNot = j.getString("bee_or_not");
                    Log.i("Bee Or Not", beeOrNot);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return beeOrNot;
        }
        @Override
        protected void onPostExecute(String beeOrNot) {

            System.out.println(beeOrNot);
            String bee = beeOrNot;
            if (bee.equals("not bee")) {
                //Log.i("Code", "working");
                //Intent intent = new Intent(TakeAnotherPicOrGetHealth.this, NotABeeActivity.class);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),"There is no Bee in the image. Please upload another photo", Toast.LENGTH_LONG).show();
            }
            else {
                //Log.i("Code", "not working");
                httpGetAsyncTask getResult = new httpGetAsyncTask();
                getResult.execute();
            }


        }
    }
    private class httpGetAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {

            String REQUEST_METHOD = "GET";
            int READ_TIMEOUT = 15000;
            int CONNECTION_TIMEOUT = 15000;
            String stringUrl = "https://drhoneeb.herokuapp.com/test";
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);         //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();         //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();       //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());         //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();         //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }         //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();         //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            //resultTextView.setText(response);
            String health = null;
            String description = null;
            String species = null;
            result = result.replaceAll("\\\\","");
            int length = result.length();
            //String newString = result.substring(1,length-1);
            Log.i("result", result);
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray jArray = obj.getJSONArray("response");
                JSONObject j = jArray.getJSONObject(0);
                health = j.getString("status");
                description = j.getString("problem");
                species = j.getString("species");
                Log.i("bee health", health);
                Log.i("bee health description", description);
                Log.i("bee species", species);
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



}

