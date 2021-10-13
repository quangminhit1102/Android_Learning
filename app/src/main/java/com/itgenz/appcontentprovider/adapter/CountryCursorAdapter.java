package com.itgenz.appcontentprovider.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.itgenz.appcontentprovider.R;

public class CountryCursorAdapter extends CursorAdapter
{
    // Constructor
    public CountryCursorAdapter(Context context, Cursor c) {
        super(context,c,0);
    }
    // Inflater View of the Item
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.country_info,parent,false);
    }
    // Binding all data to given View
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find all item
        TextView code = view.findViewById(R.id.code);
        TextView name = view.findViewById(R.id.name);
        TextView continent = view.findViewById(R.id.continent);

        // Get Data from cursor
        String codeTxt = cursor.getString(1);
        String nameTxt = cursor.getString(2);
        String continentTxt = cursor.getString(3);

        // Binding data to item
        code.setText(codeTxt);
        name.setText(nameTxt);
        continent.setText(continentTxt);


    }
}
