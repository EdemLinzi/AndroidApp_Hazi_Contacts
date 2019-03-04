package com.example.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydatabase.db";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TODO_TABLE = "CREATE_TABLE "+ MyDataBase.Entry.TABLE_NAME +"("
                + MyDataBase.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MyDataBase.Entry.NAME + " TEXT NOT NULL,"
                + MyDataBase.Entry.ADDRESS + " TEXT NOT NULL,"
                + MyDataBase.Entry.PHONE + " TEXT NOT NULL,"
                + MyDataBase.Entry.EMAIL + " TEXT NOT NULL );";


        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyDataBase.Entry.TABLE_NAME);
        onCreate(db);
    }
}
