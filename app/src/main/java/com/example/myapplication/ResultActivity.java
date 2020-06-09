package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MoreInfoActivies.CarnionActivity;
import com.example.myapplication.MoreInfoActivies.HybridActivity;
import com.example.myapplication.MoreInfoActivies.ItalianActivity;
import com.example.myapplication.MoreInfoActivies.RussianActivity;
import com.example.myapplication.Solutions.AntSolutionActivity;
import com.example.myapplication.Solutions.RobbedActivity;
import com.example.myapplication.Solutions.VarroaSolutionActivity;

import java.io.ByteArrayOutputStream;

//This class is used to show the health information of the bee.
public class ResultActivity extends AppCompatActivity {

    Button beeHealth, addToHistroyBtn;
    ImageView beeTv;
    MyDbHelper dbHelper;
    Uri imageUri;
    TextView healthCondition;
    TextView healthDescription;
    TextView beeSpecies;
    TextView healProblem;
    String hCond;
    String hDesc;
    String bSpecs;
    Button seeMoreBtn;
    Button findSulotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.App);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        Intent intent_upload = getIntent();
        hCond = intent_upload.getStringExtra("bee health condition");
        hDesc = intent_upload.getStringExtra("bee health description");
        bSpecs = intent_upload.getStringExtra("bee species");
        if (bSpecs == null) {
            Intent intent = new Intent(ResultActivity.this, NotABeeActivity.class);
            startActivity(intent);
        } else {
            healthCondition = (TextView) findViewById(R.id.editText_healthCondition_);
            healthCondition.setText(hCond);
            healthDescription = (TextView) findViewById(R.id.text_problem);
            healProblem = (TextView) findViewById(R.id.text_description);
            healthDescription.setText(hDesc);
            beeSpecies = (TextView) findViewById(R.id.text_species);
            beeSpecies.setText(bSpecs);
            //Initializing "Get health for another bee" button.
            beeHealth = (Button) findViewById(R.id.button_anotherBeeHealth);
            beeTv = (ImageView) findViewById(R.id.image_bee);
            addToHistroyBtn = (Button) findViewById(R.id.addToHistoryBtn);
            Globals g = Globals.getInstance();
            byte[] picture = g.getData();
            Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageUri = getImageUri(this,bmp);
            beeTv.setImageURI(imageUri);
            //When clicking on "Get health for another bee" button.
            seeMoreBtn = (Button) findViewById(R.id.see_moreBtn);
            seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String italian = "Italian Honey Bee";
                    String russia = "Russian Honey Bee";
                    String carnion = "Carniolan Honey Bee";
                    String hybrid = "Hybrid Breed Honey Bee";
                    if (bSpecs.equalsIgnoreCase(italian)) {
                        Intent intent = new Intent(ResultActivity.this, ItalianActivity.class);
                        startActivity(intent);
                    }else if (bSpecs.equalsIgnoreCase(russia)) {
                        Intent intent = new Intent(ResultActivity.this, RussianActivity.class);
                        startActivity(intent);
                    }else if (bSpecs.equalsIgnoreCase(carnion)) {
                        Intent intent = new Intent(ResultActivity.this, CarnionActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(ResultActivity.this, HybridActivity.class);
                        startActivity(intent);
                    }
                }
            });
            findSulotion = (Button) findViewById(R.id.buttonFindSolution);
            if (hCond.equalsIgnoreCase("healthy")) {
                findSulotion.setVisibility(View.GONE);
                healProblem.setVisibility(View.INVISIBLE);
                healthDescription.setVisibility(View.INVISIBLE);
            }
            else {
                String ant = "Ant Infestation";
                String rob =  "Robbed Hive";
                String var = "Varroa Mite Infestation";
                if (hDesc.equalsIgnoreCase(ant)) {
                    findSulotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ResultActivity.this, AntSolutionActivity.class);
                            startActivity(intent);
                        }
                    });
                } else if (hDesc.equalsIgnoreCase(rob)) {
                    findSulotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ResultActivity.this, RobbedActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    findSulotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ResultActivity.this, VarroaSolutionActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            beeHealth.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Intent intent_another = new Intent(ResultActivity.this, MainActivity.class);
                    startActivity(intent_another);
                }
            }));

            dbHelper = new MyDbHelper(this);

            addToHistroyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputData();
                }
            });
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    private String randomGenerator() {
//        String[] speciesList = {"Italian Honey Bee", "Russian Honey Bee", "Carniolan Honey Bee", "Hybrid Breed Honey Bee"};
//        int index = (int) (Math.random() * speciesList.length);
//        return speciesList[index];
//    }

    private void inputData() {
        String timestamp = "" + System.currentTimeMillis();

        long id = dbHelper.insertRecord(
                ""+bSpecs,
                ""+hCond,
                ""+hDesc,
                ""+imageUri,
                ""+timestamp,
                ""+timestamp
        );

        Toast.makeText(this,"Picture has been save to history!", Toast.LENGTH_SHORT).show();
    }
}
