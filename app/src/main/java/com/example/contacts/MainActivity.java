package com.example.contacts;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<Contacts_Array> contacts;
    ContactAdapter contactAdapter;
    ListView listView;
    Boolean permission;

    public static class Contacts_Array{
        public String contacts_Array_ID = "";
        public String contacts_Array_Name = "";
        public String contacts_Array_Phone = "";
        public String contacts_Array_Address = "";
        public String contacts_Array_Email = "";
        public String contacts_Array_Image = "";
    }

    private String[] columnProjection = new String[]{


            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            //ContactsContract.CommonDataKinds.Phone.NUMBER

            //TODO saját adatbázishoz
            /*MyContentProvider.NAME,
            MyContentProvider.ADDRESS,
            MyContentProvider.EMAIL,
            MyContentProvider.PHONE,
            MyContentProvider.IMAGE*/

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = ((ListView)findViewById(R.id.list_view));
        contacts = new ArrayList<Contacts_Array>();
        permission = false;
        perrmissionRequest();

        contactAdapter = new ContactAdapter(this,contacts);
        listView.setAdapter(contactAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity","An item is clicked");

                //TODO a képre is gondolni kell
                Intent intent = new Intent(view.getContext(),ContactData2.class);
                intent.putExtra("ID",contacts.get(position).contacts_Array_ID);
                Log.i("MainActivity","This is the id "+contacts.get(position).contacts_Array_ID);

                startActivityForResult(intent,1);

            }
        });

    }

    public void refresh(View v){
        Log.i("MainActivity","loadContact");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED) {

            if (getSupportLoaderManager().getLoader(0) != null) {
                getSupportLoaderManager().initLoader(1, null, this);
                Log.i("MainActivity", "initLoader");

            } else {
                getSupportLoaderManager().restartLoader(1, new Bundle(), this);
                Log.i("MainActivity", "restartLoader");

            }
        }
    }

    public void startAddContact(View v){
        Log.i("MainActivity","startAddContact");

        //TODO egyszerűbb és jobb
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        startActivityForResult(intent,1);

        //TODO régi
        //startActivity(new Intent(this,AddContactActivity.class));
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.i("MainActivity","onCreateLoader");

        if(i==1){

            return new CursorLoader(this,ContactsContract.Contacts.CONTENT_URI,columnProjection,null,null,null);
            //TODO saját adatbázishoz
            //return new CursorLoader(this,MyContentProvider.CONTENT_URI,columnProjection,null,null,null);
        }
        return null;
    }



    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.i("MainActivity","onLoadFinished");
        //TODO saját adatbázishoz
        /*
        contacts.clear();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){

                Contacts_Array contacts_array = new Contacts_Array();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                contacts_array.contacts_Array_Name = name;
                contacts_array.contacts_Array_Phone = phone;
                contacts_array.contacts_Array_Email = email;
                contacts_array.contacts_Array_Address = address;

                contacts.add(contacts_array);


            }
        }
        cursor.close();
        contactAdapter.notifyDataSetChanged();
    */
     //TODO eddig

        contacts.clear();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){

                Contacts_Array contacts_array = new Contacts_Array();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                contacts_array.contacts_Array_Name = name;
                contacts_array.contacts_Array_ID = id;

                contacts.add(contacts_array);


            }
        }
        //cursor.close();
        contactAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MainActivity","onActivityResult");

        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                contactAdapter.notifyDataSetChanged();
                getSupportLoaderManager().restartLoader(1,new Bundle(),this);

            }
        }

    }

    public void perrmissionRequest(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_CONTACTS},1);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permisson GRANTED",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Permisson DENIED ",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
