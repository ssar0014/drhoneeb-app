package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import javax.security.auth.Destroyable;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertRecord(String species, String health_condition, String description,
                             String image, String addedTime, String updatedTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.C_SPECIES, species);
        values.put(Constants.C_HEALTH_CONDITION, health_condition);
        values.put(Constants.C_DESCRIPTION, description);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATED_TIMESTAMP, updatedTime);

        long id = db.insert(Constants.TABLE_NAME, null, values);

        db.close();
        return id;
    }


    //get all data
    public ArrayList<ModelRecord> getAllRecords(String orderBy) {
        //Orderby query will allow to sort data e.g. newest/oldest first, name ascending
        //it will return list or records since we have used return type Arraylist<ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all records and add to list
        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_SPECIES)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_HEALTH_CONDITION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                //add record to list
                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }

        //close db connection
        db.close();

        //return the list
        return recordsList;
    }

    //serch data
    public ArrayList<ModelRecord> searchRecords(String query) {
        //Orderby query will allow to sort data e.g. newest/oldest first, name ascending
        //it will return list or records since we have used return type Arraylist<ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_SPECIES + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all records and add to list
        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_SPECIES)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_HEALTH_CONDITION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                //add record to list
                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }

        //close db connection
        db.close();

        //return the list
        return recordsList;
    }

    //get number of records
    public int getRecordsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
