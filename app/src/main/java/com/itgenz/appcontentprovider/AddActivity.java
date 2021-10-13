package com.itgenz.appcontentprovider;

import static com.itgenz.appcontentprovider.constant.URIConstant.CONTENT_URI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private Spinner spn;
    private Button btnSave;
    private EditText edtCode, edtName;

    // On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Find all item view
        spn = findViewById(R.id.continentList);
        btnSave = findViewById(R.id.save);
        edtCode = findViewById(R.id.code);
        edtName = findViewById(R.id.name);
        //Init Spinner Data
        String[] listContinent = {"Asia", "Africa", "Europe", "North America", "South America", "Australia", "Antarctica"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listContinent);
        spn.setAdapter(spnAdapter);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check Input Required
                if(edtCode.getText().toString().equalsIgnoreCase(""))
                {
                    edtCode.setError("Required Field!");
                }
                else if(edtName.getText().toString().equalsIgnoreCase(""))
                {
                    edtName.setError("Required Field!");
                }
                else
                {
                    String code = edtCode.getText().toString();
                    String name = edtName.getText().toString();
                    String continent = spn.getSelectedItem().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("code", code);
                    contentValues.put("name", name);
                    contentValues.put("continent", continent);
                    Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);
                    if(Integer.parseInt(uri.getPathSegments().get(1)) == -1)
                    {
                        Toast.makeText(getApplicationContext(), "Code has already exists!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AddActivity.this, "Add country Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this, MainActivity.class));
                        finish();
                    }
                }

            }
        });
    }
}