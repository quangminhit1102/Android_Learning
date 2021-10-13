package com.itgenz.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TheWorld";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Oncreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        CountriesDB.onCreate(db);
    }

    //On Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CountriesDB.onUpgrade(db, oldVersion, newVersion);
    }
}
