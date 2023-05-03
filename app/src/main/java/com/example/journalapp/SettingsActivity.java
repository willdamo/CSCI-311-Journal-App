package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
/*
TODO LIST:
[]- Add all the spinners including:
    []- Color Spinners
    []- Theme Spinner
[]- Create a new Database or tweak the existing one for two types instead of one (for updated settings)
[]- Add a save button to initiate saves
[]- Add a back button with same functionality as open entry activity
[]- Make a method to set the 4 themes when it is saved
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

/*
    public void setColorSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
    }*/


}