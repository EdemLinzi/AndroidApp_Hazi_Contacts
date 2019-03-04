package com.example.contacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    Context myContext;
    List<MainActivity.Contacts_Array> myContacts;

    ContactAdapter(Context context, List<MainActivity.Contacts_Array> contacts_arrays){
        this.myContext = context;
        this.myContacts = contacts_arrays;
    }

    @Override
    public int getCount() {
        return myContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return myContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(myContext,R.layout.list_item,null);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewPhone = view.findViewById(R.id.textViewPhone);

        textViewName.setText(myContacts.get(position).contacts_Array_Name);
        textViewPhone.setText(myContacts.get(position).contacts_Array_Phone);

        view.setTag(myContacts.get(position).contacts_Array_Name);
        return view;

    }
}
