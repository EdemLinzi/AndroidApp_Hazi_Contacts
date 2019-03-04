package com.example.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactData2 extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText phone;
    EditText email;
    Button btn;
    Button btn_save;
    Button btn_delete;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ContactData2","onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_data_layout2);

        name = findViewById(R.id.data_name);
        address = findViewById(R.id.data_address);
        phone = findViewById(R.id.data_phone);
        email = findViewById(R.id.data_email);
        btn = findViewById(R.id.button);
        btn_save = findViewById(R.id.btn_contact_data_save);
        btn_delete = findViewById(R.id.btn_contact_data_delete);

        Intent intent =  getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Log.i("ContactData2",""+id);


        Cursor cursorContact = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, ContactsContract.Contacts._ID+" = "+id,null,null);
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, "CONTACT_ID = "+id,null,null);
        Cursor cursorAddress = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,null,"CONTACT_ID = "+id,null,null);
        Cursor cursorEmail = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,"CONTACT_ID = "+id,null,null);

        //TODO saját adatbázhoz kell
        //cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,null,null,null,null);

        //int position = intent.getIntExtra("Position",-1);


        /*cursorContact.moveToPosition(position);
        cursorPhone.moveToPosition(position);
        cursorAddress.moveToPosition(position);
        cursorEmail.moveToPosition(position);*/

        //TODO saját adatbázishoz kell
        /*id = cursor.getInt(cursor.getColumnIndex("_id"));
        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        address.setText(cursor.getString(cursor.getColumnIndex("address")));
        phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
        email.setText(cursor.getString(cursor.getColumnIndex("email")));*/

        //id = cursorContact.getInt(cursorContact.getColumnIndex(ContactsContract.Contacts._ID));
        while(cursorContact.moveToNext()) {
            name.setText(cursorContact.getString(cursorContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        }
        while(cursorPhone.moveToNext()){
            phone.setText(cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }
        String emailStr = "Faszom nem jó :(";
        while(cursorEmail.moveToNext()) {
            emailStr = cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
        }
        email.setText(emailStr);
        while(cursorAddress.moveToNext()) {
            String fullAddress = cursorAddress.getString(cursorAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                    + cursorAddress.getString(cursorAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                    + cursorAddress.getString(cursorAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
            String addressTemp = fullAddress;
            address.setText(fullAddress);
        }
         cursorContact.close();
         cursorPhone.close();
         cursorAddress.close();
         cursorEmail.close();

        setResult(RESULT_OK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ContactData2","onDestroy");

    }

    public void back(View v){
        finish();
    }

    public void deleteContact(View v){
        getContentResolver().delete(MyContentProvider.CONTENT_URI,MyContentProvider._ID+" = "+id,null);
        setResult(RESULT_OK);
        finish();

    }

    public void updateContact(View v){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyContentProvider.NAME,name.getText().toString());
        contentValues.put(MyContentProvider.ADDRESS,address.getText().toString());
        contentValues.put(MyContentProvider.PHONE,phone.getText().toString());
        contentValues.put(MyContentProvider.EMAIL,email.getText().toString());


        getContentResolver().update(MyContentProvider.CONTENT_URI,contentValues,MyContentProvider._ID+" = "+id,null);
        setResult(RESULT_OK);

        finish();
    }

}
