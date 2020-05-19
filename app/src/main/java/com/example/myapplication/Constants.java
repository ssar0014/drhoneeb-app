package com.example.myapplication;

public class Constants {


    public static final String DB_NAME = "MY_RECORDS_DB";

    public static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "MY_RECORDS_TABLE";

    public static final String C_ID = "ID";

    public static final String C_SPECIES = "SPECIES";

    public static final String C_HEALTH_CONDITION = "HEALTH_CONDITION";

    public static final String C_DESCRIPTION = "DESCRIPTION";

    public static final String C_IMAGE = "IMAGE";

    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";

    public static final String C_UPDATED_TIMESTAMP = "UPDATED_TIME_STAMP";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_SPECIES + " TEXT,"
            + C_HEALTH_CONDITION + " TEXT,"
            + C_DESCRIPTION + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADDED_TIMESTAMP + " TEXT,"
            + C_UPDATED_TIMESTAMP + " TEXT"
            + ")";
}
