package com.example.contacts;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddContactActivity extends AppCompatActivity {

    ContentResolver contentResolver;

    EditText nickName;
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

        nickName = findViewById(R.id.et_contact_nickname);
        name = findViewById(R.id.et_contact_name);
        address = findViewById(R.id.et_contact_address);
        phone = findViewById(R.id.et_contact_phone);
        email = findViewById(R.id.et_contact_email);
        btnSave = findViewById(R.id.btn_save);

        contentResolver=getContentResolver();


    }


    public void saveValues(View v){
        Log.i("AddContactActivity","Save Contact");
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyContentProvider.NAME,name.getText().toString());
        contentValues.put(MyContentProvider.ADDRESS,address.getText().toString());
        contentValues.put(MyContentProvider.PHONE,phone.getText().toString());
        contentValues.put(MyContentProvider.EMAIL,email.getText().toString());

       contentResolver.insert(MyContentProvider.CONTENT_URI,contentValues);
       setResult(RESULT_OK);

       finish();
    }



}
