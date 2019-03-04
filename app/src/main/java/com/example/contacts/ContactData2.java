package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactData2 extends AppCompatActivity {

    TextView name;
    TextView phone;
    TextView address;
    TextView email;
    Button btn;

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

        Intent intent =  getIntent();
       // String log = intent.getStringExtra("ContactName");
       // Log.i("ContactData2",""+log);

        name.setText(intent.getStringExtra("ContactName"));
        address.setText(intent.getStringExtra("ContactAddress"));
        phone.setText(intent.getStringExtra("ContactPhone"));
        email.setText(intent.getStringExtra("ContactEmail"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ContactData2","onDestroy");

    }

    public void back(View v){
        finish();
    }
}
