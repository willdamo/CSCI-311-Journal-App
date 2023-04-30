package com.example.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insert(String title, String month, String day, String year, String journal) {
        //stores information for database
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("month", month);
        values.put("day", day);
        values.put("year", year);
        values.put("journal", journal);
        return database.insert("entries", null, values) > 0;
    }

    public void delete(String title){
        database.delete("entries", "title=\""+title+"\"", null);
    }

    public String getMonth(String title) {
        String query = "select month from entries where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String month = cursor.getString(0);
        cursor.close();
        return month;
    }

    public String getDay(String title) {
        String query = "select day from entries where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String day = cursor.getString(0);
        cursor.close();
        return day;
    }

    public String getYear(String title) {
        String query = "select year from entries where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String year = cursor.getString(0);
        cursor.close();
        return year;
    }

    public String getDate(String title){
        String month = getMonth(title);
        String day = getDay(title);
        String year = getYear(title);

        return month+" "+day+", "+year;
    }

    public String getJournal(String title) {
        String query = "select journal from entries where title=\""+title+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String journal = cursor.getString(0);
        cursor.close();
        return journal;
    }

    public String[] getTitles(){
        String query = "select title from entries";
        ArrayList<String> array = new ArrayList<String>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            array.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return array.toArray(new String[array.size()]);
    }
}
