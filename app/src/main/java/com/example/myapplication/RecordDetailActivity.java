package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.Calendar;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {


    private CircularImageView profileIv;
    private TextView speciesTv, health_conditionTv, desctiptionTv, addedTimeTv, updatedTimeTv;
    //db helper
    MyDbHelper dbHelper;


    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);


        //get reocrd id from adapter throught intent

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        dbHelper = new MyDbHelper(this);

        profileIv = findViewById(R.id.bee_image);
        speciesTv = findViewById(R.id.speciesTv);
        health_conditionTv = findViewById(R.id.healthConditionTv);
        desctiptionTv = findViewById(R.id.descriptionTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        showRecordDetails();

    }

    private void showRecordDetails() {
        //get records details

        //query to select records based on record id
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + recordID+ "\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //keep checking in whole db for that record
        if (cursor.moveToFirst()){
            do {
                String id = ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String species = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_SPECIES));
                String health_condition = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_HEALTH_CONDITION));
                String description = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION));
                String image = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String timestampAdded = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                String timestampUpdated = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP));

                //convert timestamp to dd/mm/yy
                Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
                calendar1.setTimeInMillis(Long.parseLong(timestampAdded));
                String timeAdded = "" +DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1);

                Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
                calendar1.setTimeInMillis(Long.parseLong(timestampUpdated));
                String timeUpdated = "" +DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2);

                //set data

                speciesTv.setText(species);
                health_conditionTv.setText(health_condition);
                desctiptionTv.setText(description);
                addedTimeTv.setText(timeAdded);
                updatedTimeTv.setText(timeUpdated);
                if (image.equals("null")) {
                    //no image in record
                    profileIv.setImageResource(R.drawable.beetest);
                }
                else {
                    //have image in record
                    profileIv.setImageURI(Uri.parse(image));
                }


            }while (cursor.moveToNext());

        }


        //close db connection
        db.close();

    }


}
