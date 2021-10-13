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

public class EditActivity extends AppCompatActivity {
    private Spinner spn;
    private Button btnSave, btnDelete;
    private EditText edtCode, edtName;
    private String id; // Store ID of ROW in Country Table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // Find all item view
        spn = findViewById(R.id.continentList);
        btnDelete = findViewById(R.id.delete);
        btnSave = findViewById(R.id.save);
        edtCode = findViewById(R.id.code);
        edtName = findViewById(R.id.name);
        //Init Spinner Data
        String[] listContinent = {"Asia", "Africa", "Europe", "North America", "South America", "Australia", "Antarctica"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listContinent);
        spn.setAdapter(spnAdapter);

        Intent intent = getIntent();
        int _id = intent.getIntExtra("_id", 0);
        id = _id+"";
        String name = intent.getStringExtra("name");
        String code = intent.getStringExtra("code");
        String continent = intent.getStringExtra("continent");

        edtCode.setText(code);
        edtName.setText(name);
        for (int i = 0; i < listContinent.length; i++) {
            if(listContinent[i].equals(continent))
            {
                spn.setSelection(i);
                break;
            }
        }
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
                    Uri uri = Uri.parse(CONTENT_URI + "/" + id);
                    String code = edtCode.getText().toString();
                    String name = edtName.getText().toString();
                    String continent = spn.getSelectedItem().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("code", code);
                    contentValues.put("name", name);
                    contentValues.put("continent", continent);
                    int result = getContentResolver().update(uri, contentValues,null,null);
                    if(result == -1)
                    {
                        Toast.makeText(getApplicationContext(), "Code has already exists!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(EditActivity.this, "Update country Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(CONTENT_URI +"/" + id);
                int result = getContentResolver().delete(uri, null, null);
                if(result==1)
                {
                    Toast.makeText(EditActivity.this, "Delete country Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(EditActivity.this, "Error! Can't Delete Country", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}