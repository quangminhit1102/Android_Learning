package com.itgenz.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    // Init DBHelper
    private MyDatabaseHelper dbHelper;
    // Config
    private static final int ALL_COUNTRIES = 1;
    private static final int SINGLE_COUNTRY = 2;

    // URI Authority
    private static final String AUTHORITY = "com.itgenz.contentprovider";
    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/countries"); // Convert String to URI type

    // URI Matcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "countries", ALL_COUNTRIES);
        uriMatcher.addURI(AUTHORITY, "countries/#", SINGLE_COUNTRY);
        // Nếu URI match Authority và Path => trả về code => getType
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    // Query
    //Return the MIME type corresponding to a content URI
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String where, @Nullable String[] whereArgs, @Nullable String orderBy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CountriesDB.SQLITE_TABLE);

        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            case SINGLE_COUNTRY:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(CountriesDB.KEY_ROWID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, columns, where,
                whereArgs, null, null, orderBy);
        return cursor;
    }
    // Get Type of URI
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                return "vnd.android.cursor.dir/vnd.com.itgenz.contentprovider.countries"; // Directory
            case SINGLE_COUNTRY:
                return "vnd.android.cursor.item/vnd.com.itgenz.contentprovider.countries"; // Item
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // Insert
    // return CONTENT_URI/-1 if code already exists
    // return CONTENT_URI/id if Insert successes
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id;
        //Catch SQLiteConstraintException UNION "Code" column
        try{
            id = db.insert(CountriesDB.SQLITE_TABLE, null, values);
        }
        catch (SQLiteConstraintException ex)
        {
            id = -1;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    // Delete
    // Return Count of Row Deleted
    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            case SINGLE_COUNTRY:
                String id = uri.getPathSegments().get(1);
                where = CountriesDB.KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(where) ?
                        " AND (" + where + ')' : "");

                // Where row=id => query by ID
                //1: where !=null => add where query
                //2:  else => add ""
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(CountriesDB.SQLITE_TABLE, where, whereArgs);
        // the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.
        getContext().getContentResolver().notifyChange(uri, null);
        //
        return deleteCount;
    }

    // Update
    // return -1 if code already exists
    // return update count if Update successes
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_COUNTRIES:
                //do nothing
                break;
            case SINGLE_COUNTRY:
                String id = uri.getPathSegments().get(1);
                where = CountriesDB.KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(where) ?
                        " AND (" + where + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount;
        //Catch SQLiteConstraintException UNION "Code" column
        try
        {
            updateCount = db.update(CountriesDB.SQLITE_TABLE, values, where, whereArgs);
        }
        catch (SQLiteConstraintException ex)
        {
            return -1;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}
