package com.example.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class MyAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    ArrayList<MainActivity.Contacts_Array> contacts;
    Context context;
    public MyAsyncTaskLoader(@NonNull Context context, ArrayList<MainActivity.Contacts_Array> contacts) {
        super(context);

        this.context = context;
        this.contacts = contacts;

    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.i("MyAsyncTaskLoader","loadInBackground");
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        contacts.clear();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){

                MainActivity.Contacts_Array contacts_array = new MainActivity.Contacts_Array();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));

                String fullAddress =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                        + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                        + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                String address = fullAddress;
                String image = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                contacts_array.contacts_Array_Name = name;
                contacts_array.contacts_Array_Phone = phone;
                contacts_array.contacts_Array_Email = email;
                contacts_array.contacts_Array_Address = address;
                contacts_array.contacts_Array_Image = image;

                contacts.add(contacts_array);


            }
        }
        cursor.close();

        return "Finished";
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
