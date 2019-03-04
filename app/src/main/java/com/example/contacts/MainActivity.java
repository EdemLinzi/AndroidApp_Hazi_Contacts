package com.example.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<Contacts_Array> contacts;
    ContactAdapter contactAdapter;
    ListView listView;

    public class Contacts_Array{
        public String contacts_Array_Name = "";
        public String contacts_Array_Phone = "";
        public String contacts_Array_Address = "";
        public String contacts_Array_Email = "";
        public String contacts_Array_Image = "";
    }

    private String[] columnProjection = new String[]{
            MyContentProvider.NAME,
            MyContentProvider.ADDRESS,
            MyContentProvider.EMAIL,
            MyContentProvider.PHONE,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = ((ListView)findViewById(R.id.list_view));
        contacts = new ArrayList<Contacts_Array>();


        if(getSupportLoaderManager().getLoader(0)==null){
            getSupportLoaderManager().initLoader(1,null,this);
        }

        contactAdapter = new ContactAdapter(this,contacts);
        listView.setAdapter(contactAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity","An item is clicked");

                Intent intent = new Intent(view.getContext(),ContactData2.class);
                intent.putExtra("Position",position);
                intent.putExtra("ID",id);
                Log.i("MainActivity","This is the id "+id);

                startActivityForResult(intent,1);

            }
        });

    }
    public void refresh(View v){
        Log.i("MainActivity","refresh");
        getSupportLoaderManager().restartLoader(1,new Bundle(),this);
    }

    public void startAddContact(View v){
        Log.i("MainActivity","startAddContact");
        startActivity(new Intent(this,AddContactActivity.class));
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.i("MainActivity","onCreateLoader");

        if(i==1){
            Log.i("MainActivity","new CursorLoader");

            //TODO saját adatbázishoz
            return new CursorLoader(this,MyContentProvider.CONTENT_URI,columnProjection,null,null,null);
        }
        return null;
    }



    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.i("MainActivity","onLoadFinished");
        contacts.clear();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){

                //TODO saját adatbázishoz
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
}
