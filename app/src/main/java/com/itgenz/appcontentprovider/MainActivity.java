package com.itgenz.appcontentprovider;

import static com.itgenz.appcontentprovider.constant.CountryDBConstant.KEY_CODE_COL;
import static com.itgenz.appcontentprovider.constant.CountryDBConstant.KEY_CONTINENT_COL;
import static com.itgenz.appcontentprovider.constant.CountryDBConstant.KEY_NAME_COL;
import static com.itgenz.appcontentprovider.constant.CountryDBConstant.KEY_ROWID_COL;
import static com.itgenz.appcontentprovider.constant.URIConstant.CONTENT_URI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.itgenz.appcontentprovider.adapter.CountryCursorAdapter;
import com.itgenz.appcontentprovider.model.Country;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Button Add new Country
    private Button btn ;
    // List View Countries
    private ListView lv;

    // Countries list
    ArrayList<Country> lCountry = new ArrayList<>();
    // Cursor Adapter
    public static CountryCursorAdapter cAdapter;

    // On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hind Support Bar
        getSupportActionBar().hide();
        // Match needed item view
        btn = findViewById(R.id.add);
        lv = findViewById(R.id.countryList);
        //====================================================================================================================
        // Query with Content Resolver
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(CONTENT_URI,null,null ,null, null);
        while(c.moveToNext())
        {
            int _id = Integer.parseInt(c.getString(KEY_ROWID_COL));
            String code = c.getString(KEY_CODE_COL);
            String name = c.getString(KEY_NAME_COL);
            String continent = c.getString(KEY_CONTINENT_COL);
            lCountry.add(new Country(_id, code, name, continent));
        }
        // Init Cursor adpter
        cAdapter = new CountryCursorAdapter(MainActivity.this,c);
        // Set Adapter
        lv.setAdapter(cAdapter);
        // Add Button Click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        // List View item Click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Country country = lCountry.get(i);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("_id", country._id);
                intent.putExtra("code", country.code);
                intent.putExtra("name", country.name);
                intent.putExtra("continent", country.continent);
                startActivity(intent);
            }
        });

    }
}