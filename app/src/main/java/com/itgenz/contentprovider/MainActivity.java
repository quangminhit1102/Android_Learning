package com.itgenz.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //=====================================================================
        //Log testing
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,null,null, null,null);
        while (c.moveToNext())
        {
            int temp = c.getColumnIndex(CountriesDB.KEY_NAME);
            if(temp != -1)
            {
                Log.d("Main", c.getString(temp));
            }
        }
    }
}