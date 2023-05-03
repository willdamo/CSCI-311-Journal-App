package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    String ogTitle;
    EditText changeJournalName;
    Spinner themeSpinner;
    Spinner saveColorSpinner;
    Spinner deleteColorSpinner;
    Spinner backColorSpinner;
    Spinner addEditColorSpinner;
    Spinner textColorSpinner;
    Spinner backgroundColorSpinner;
    Button backButton;
    Button saveButton;
    String newTitle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent i = getIntent();
        ogTitle = i.getStringExtra("journalName");

        //initializing the spinners and edit text and buttons
        changeJournalName = findViewById(R.id.changeJournalName);

        backButton = findViewById(R.id.settingsBackButton);
        saveButton = findViewById(R.id.settingsSaveButton);

        themeSpinner = findViewById(R.id.themeSpinner);
        saveColorSpinner = findViewById(R.id.saveColorSpinner);
        deleteColorSpinner = findViewById(R.id.deleteColorSpinner);
        backColorSpinner = findViewById(R.id.backColorSpinner);
        addEditColorSpinner = findViewById(R.id.addEditColorSpinner);
        textColorSpinner = findViewById(R.id.textColorSpinner);
        backgroundColorSpinner = findViewById(R.id.backColorSpinner);

        //setting buttons
        setBackButton();
        setSaveButton();

        //sets all the spinners
        setColorSpinners();
        setThemeSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //isChanged();
    }

    public void setBackButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void setSaveButton(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
                //if(isChanged()) {
                    //updateSettingsFile();
                //}
            }
        });
    }
/*
    public void updateSettingsFile(){
        if(isChanged()){
            SharedPreferences settingsFile = getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settingsFile.edit();

            String newTheme = ((TextView) themeSpinner.getSelectedView()).getText().toString();
            String newSaveColor = ((TextView) saveColorSpinner.getSelectedView()).getText().toString();
            String newDeleteColor = ((TextView) deleteColorSpinner.getSelectedView()).getText().toString();
            String newBackcolor = ((TextView) backColorSpinner.getSelectedView()).getText().toString();
            String newAddEditColor = ((TextView) addEditColorSpinner.getSelectedView()).getText().toString();
            String newTextColor = ((TextView) textColorSpinner.getSelectedView()).getText().toString();
            String newBackgroundColor = ((TextView) backgroundColorSpinner.getSelectedView()).getText().toString();

            //applying to settings file
            editor.putString("theme", newTheme);
            editor.putString("saveColor", newSaveColor);
            editor.putString("deleteColor", newDeleteColor);
            editor.putString("backColor", newBackcolor);
            editor.putString("addEditColor", newAddEditColor);
            editor.putString("textColor", newTextColor);
            editor.putString("backgroundColor", newBackgroundColor);
            if(!TextUtils.isEmpty(changeJournalName.getText().toString())) {
                String newJournal = changeJournalName.getText().toString();
                editor.putString("journalName", newJournal);
            }
            editor.apply();
        }
    }*/

    //Returns true if anything in the screen is changed
    /*
    public boolean isChanged(){
        if(!((TextView) saveColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");
            return true;
        }
        if(!((TextView) deleteColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");

            return true;
        }
        if(!((TextView) backColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");

            return true;
        }
        if(!((TextView) addEditColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");

            return true;
        }
        if(!((TextView) textColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");

            return true;
        }
        if(!((TextView) backgroundColorSpinner.getSelectedView()).getText().toString().equals("Default")){
            System.out.println("true");

            return true;
        }
        if(!TextUtils.isEmpty(changeJournalName.getText().toString()) &&
                !ogTitle.equalsIgnoreCase(changeJournalName.getText().toString())){
            System.out.println("true");

            return true;
        }
        System.out.println("false");

        return false;
    }*/

    //sets the color spinners
    public void setColorSpinners(){
        setColorSpinner(saveColorSpinner);
        setColorSpinner(deleteColorSpinner);
        setColorSpinner(backColorSpinner);
        setColorSpinner(addEditColorSpinner);
        setColorSpinner(textColorSpinner);
        setColorSpinner(backgroundColorSpinner);
    }

    //sets the theme spinner
    public void setThemeSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
    }

    //applies the Colors string array from strings xml
    public void setColorSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}