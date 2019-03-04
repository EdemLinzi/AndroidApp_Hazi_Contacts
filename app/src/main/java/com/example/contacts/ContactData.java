package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ContactData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ContactData","onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_data_layout);



    }


}
