package com.example.journalapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperEntries extends SQLiteOpenHelper {

    public DatabaseHelperEntries(@Nullable Context context){
        super(context, "entries", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCommand = "create table entries(_id integer primary key autoincrement, " +
                "title text, month text, day text, year text, journal text);";
        sqLiteDatabase.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists entries");
        onCreate(sqLiteDatabase);
    }
}
