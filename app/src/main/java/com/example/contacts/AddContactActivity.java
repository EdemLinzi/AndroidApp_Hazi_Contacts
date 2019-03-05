package com.example.contacts;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddContactActivity extends AppCompatActivity {

    ContentResolver contentResolver;

    EditText name;
    EditText address;
    EditText phone;
    EditText email;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("AddContactActivity","onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);

        name = findViewById(R.id.et_contact_name);
        address = findViewById(R.id.et_contact_address);
        phone = findViewById(R.id.et_contact_phone);
        email = findViewById(R.id.et_contact_email);
        btnSave = findViewById(R.id.btn_save);

        contentResolver=getContentResolver();


    }


    public void saveValues(View v){

        //TODO már nincs rá szükség
        Log.i("AddContactActivity","Save Contact");
        ContentValues contentValues = new ContentValues();

        /*contentValues.put(ContactsContract.Contacts.DISPLAY_NAME,name.getText().toString());
        //TODO szétbontani az addresst
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.CITY,address.getText().toString());
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER,phone.getText().toString());
        contentValues.put(ContactsContract.CommonDataKinds.Email.ADDRESS,email.getText().toString());*/

        //contentResolver.insert(ContactsContract.Contacts.CONTENT_URI,contentValues);

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        /*intent.putExtra(ContactsContract.Intents.Insert.NAME,name.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE,phone.getText());
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL,email.getText());
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL,address.getText());*/

        startActivity(intent);

        //TODO saját adatbázishoz kell
        /*
        contentValues.put(MyContentProvider.NAME,name.getText().toString());
        contentValues.put(MyContentProvider.ADDRESS,address.getText().toString());
        contentValues.put(MyContentProvider.PHONE,phone.getText().toString());
        contentValues.put(MyContentProvider.EMAIL,email.getText().toString());

       contentResolver.insert(MyContentProvider.CONTENT_URI,contentValues);
       */
       setResult(RESULT_OK);

       finish();
    }



}
