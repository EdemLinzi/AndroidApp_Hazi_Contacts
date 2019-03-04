package com.example.contacts;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "com.example.contacts.MyContentProvider";
    public static final String URL = "content://"+PROVIDER_NAME+"/contacts";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String Image = "image";

    private static HashMap<String, String> CONTACTS_PROJECTION_MAP;
    static final int CONTACTS = 1;
    static final int CONTACTS_ID = 2;

    static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS);
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", CONTACTS_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Contacts";
    static final String CONTACTS_TABLE_NAME = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + CONTACTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " address TEXT NOT NULL,"+
                    " phone TEXT NOT NULL,"+
                    " email TEXT NOT NULL);"
                    ;


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  CONTACTS_TABLE_NAME);
            onCreate(db);
        }



    }



    @Override
    public boolean onCreate() {
        Log.i("MyContentProvider","onCreate");

        Context context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        db = databaseHelper.getWritableDatabase();
        return (db==null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i("MyContentProvider","query");

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CONTACTS_TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case CONTACTS: {
                queryBuilder.setProjectionMap(CONTACTS_PROJECTION_MAP);
                break;
            }
            case CONTACTS_ID:
                queryBuilder.appendWhere(_ID + "="+uri.getPathSegments().get(1));
                break;
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        Log.i("MyContentProvider","insert");
        long rowID = db.insert(CONTACTS_TABLE_NAME,"",values);
        if(rowID>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            Log.i("MyContentProvider","insert succesfull");

            return _uri;
        }

        throw new SQLException("Failed to add a records into" + uri);
    }

    @Override
    public int delete(Uri uri,String selection, String[] selectionArgs) {
        Log.i("MyContentProvider","delete "+selection);

        db.delete(CONTACTS_TABLE_NAME,selection,selectionArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i("MyContentProvider","update");
        db.update(CONTACTS_TABLE_NAME,values,selection,selectionArgs);
        return 0;
    }
}
