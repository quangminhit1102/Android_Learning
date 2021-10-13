package com.itgenz.contentprovider;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CountriesDB {
    public static final String KEY_ROWID = "_id";// Country ID
    public static final String KEY_CODE = "code";// Code
    public static final String KEY_NAME = "name";// Name
    public static final String KEY_CONTINENT = "continent";// Continent

    private static final String LOG_TAG = "CountriesDb"; // Identify for Log
    public static final String SQLITE_TABLE = "Country"; // Table Name

    // Create statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CODE + "," +
                    KEY_NAME + "," +
                    KEY_CONTINENT + "," +
                    " UNIQUE (" + KEY_CODE +"));";

    // On Create
    public static void onCreate(SQLiteDatabase db) {
        Log.w(LOG_TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
        //Insert Example Data
        db.execSQL( "INSERT INTO "+ SQLITE_TABLE+ " values(1,'001','Viet Nam', 'Asian')");
        db.execSQL( "INSERT INTO "+ SQLITE_TABLE+ " values(2,'002','China', 'Asian')");
        db.execSQL( "INSERT INTO "+ SQLITE_TABLE+ " values(3,'003','America', 'North America')");

    }

    // On Udgrade
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }
}
