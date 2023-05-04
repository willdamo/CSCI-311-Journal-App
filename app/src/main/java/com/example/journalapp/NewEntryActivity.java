package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class NewEntryActivity extends AppCompatActivity {

    TextView monthText;
    TextView dayText;
    TextView yearText;
    View newEntryLayout;
    EditText titleInput;
    Spinner monthSpinner;
    EditText dayInput;
    EditText yearInput;
    EditText entryInput;
    Button backButton;
    Button saveButton;
    DatabaseControlEntries control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        monthText = findViewById(R.id.monthText);
        dayText = findViewById(R.id.dayText);
        yearText = findViewById(R.id.yearText);
        newEntryLayout = findViewById(R.id.newEntryLayout);
        titleInput = findViewById(R.id.titleInput);
        monthSpinner = findViewById(R.id.monthSpinner);
        dayInput = findViewById(R.id.dayInput);
        yearInput = findViewById(R.id.yearInput);
        entryInput = findViewById(R.id.entryInput);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);

        setBackButton();
        setMonthSpinner();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveButton();
            }
        });
        setLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLayout();
    }

    public void setLayout(){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);

        //checks inside theme, if theme is none then proceed to make changes to other configurations
        if(file.getString("theme", "n/a").equals("Default")){
            entryInput.setBackgroundColor(Color.parseColor("#FFFFFF"));
            newEntryLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            backButton.setBackgroundColor(Color.parseColor("#FFC107"));
            titleInput.setTextColor(Color.parseColor("#000000"));
            entryInput.setTextColor(Color.parseColor("#000000"));
            dayInput.setTextColor(Color.parseColor("#000000"));
            yearInput.setTextColor(Color.parseColor("#000000"));
            monthText.setTextColor(Color.parseColor("#000000"));
            dayText.setTextColor(Color.parseColor("#000000"));
            yearText.setTextColor(Color.parseColor("#000000"));
        } else if(file.getString("theme", "none").equals("Night Owl")){
            entryInput.setBackgroundColor(Color.parseColor("#6C6C6C"));
            newEntryLayout.setBackgroundColor(Color.parseColor("#6C6C6C"));
            backButton.setBackgroundColor(Color.parseColor("#00E1FF"));
            titleInput.setTextColor(Color.parseColor("#FFFFFF"));
            entryInput.setTextColor(Color.parseColor("#FFFFFF"));
            dayInput.setTextColor(Color.parseColor("#FFFFFF"));
            yearInput.setTextColor(Color.parseColor("#FFFFFF"));
            monthText.setTextColor(Color.parseColor("#FFFFFF"));
            dayText.setTextColor(Color.parseColor("#FFFFFF"));
            yearText.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            backButton.setBackgroundColor(Color.parseColor(getHexColors("backColor", "orange")));
            newEntryLayout.setBackgroundColor(Color.parseColor(getHexColors("backgroundColor", "white")));
            entryInput.setBackgroundColor(Color.parseColor((getHexColors("backgroundColor", "white"))));
            entryInput.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            titleInput.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            dayInput.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            yearInput.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            monthText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            dayText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            yearText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
        }
    }
    public String getHexColors(String pref, String err){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String colorName = file.getString(pref, err);
        if(colorName.equalsIgnoreCase("white")){
            return "#FFFFFF";
        }
        if(colorName.equalsIgnoreCase("black")){
            return "#000000";
        }
        if(colorName.equalsIgnoreCase("red")){
            return "#FF6969";
        }
        if(colorName.equalsIgnoreCase("blue")){
            return "#00E1FF";
        }
        if(colorName.equalsIgnoreCase("purple")){
            return "#CE74FF";
        }
        if(colorName.equalsIgnoreCase("orange")){
            return "#FFC107";
        }
        if(colorName.equalsIgnoreCase("gray")){
            return "#989898";
        }
        if(colorName.equalsIgnoreCase("dark gray")){
            return "#6C6C6C";
        }
        return "Default";
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

    public void setMonthSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }

    //sets the save button and checks if all the required items are filled out. (Title, Month,
    //  Day, and Year.
    public void setSaveButton(){
        control = new DatabaseControlEntries(this);

        int checkBoxes = 0;
        boolean checkDay = true; //true if the day input is a numerical number between 1 and 31
                                    // inclusive

        if(TextUtils.isEmpty(titleInput.getText().toString())){
            checkBoxes++;
        }
        if(((TextView) monthSpinner.getSelectedView()).getText().toString().equals("")){
            checkBoxes++;
        }
        if(TextUtils.isEmpty(dayInput.getText().toString())){
            checkBoxes++;
        }else{
            if(!isNumeric(dayInput.getText().toString())) {
                checkDay =  false;
            }else{
                int i = Integer.parseInt(dayInput.getText().toString());
                if(!(0 < i && i <= 31)){
                    checkDay = false;
                }
            }
        }
        if(TextUtils.isEmpty(yearInput.getText().toString())){
            checkBoxes++;
        }

        if(checkBoxes == 0 && checkDay) {
            String title = titleInput.getText().toString();
            String month = ((TextView) monthSpinner.getSelectedView()).getText().toString();
            String day = dayInput.getText().toString();
            String year = yearInput.getText().toString();
            String journal = entryInput.getText().toString();

            control.open();
            boolean itWorked = control.insert(title, month, day, year, journal);
            control.close();

            if (itWorked) {
                Toast.makeText(getApplicationContext(), "Added " + title + " (" + month + " " + day + ", " + year + ")", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(), "FAILED " + title + " (" + month + " " + day + ", " + year + ")", Toast.LENGTH_SHORT).show();
            }

        }else if(checkBoxes>0){
            Toast.makeText(getApplicationContext(), "("+checkBoxes+") unfilled areas. Must fill out Title, Month, Day, and/or Year.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "Invalid Day Input. Must be a number from 1-31.", Toast.LENGTH_LONG).show();
        }
    }

    //helper method to check if string is numeric or not
    public boolean isNumeric(String s){
        try{
            double d = Double.parseDouble(s);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }

}