package com.example.contacts.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyDataBase {
    public static final class Entry implements BaseColumns{
        public static final String TABLE_NAME = "contacts";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static Uri buildContactsUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }


    }

    public static final String CONTENT_AUTHORITY = "com.example.contacts";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



}
