package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//TODO LIST
/*
None
 */
public class openEntryActivity extends AppCompatActivity {

    View openEntryLayout;
    Button backButton2;
    Button editButton;
    Button deleteButton2;
    EditText titleView;
    TextView dateView;
    EditText journalView;
    DatabaseControlEntries control;
    String ogTitle;
    String ogEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_entry);

        Intent i = getIntent();
        String titleName = i.getStringExtra("title");

        control = new DatabaseControlEntries(this);

        ogTitle = titleName;
        control.open();
        ogEntry = control.getJournal(titleName);
        control.close();

        openEntryLayout = findViewById(R.id.openEntryLayout);
        backButton2 = findViewById(R.id.backButton2);
        editButton = findViewById(R.id.editButton);
        deleteButton2 = findViewById(R.id.deleteButton2);
        titleView = findViewById(R.id.titleView);
        dateView = findViewById(R.id.dateView);
        journalView = findViewById(R.id.journalView);

        journalView.setEnabled(false);
        titleView.setEnabled(false);

        journalView.setWidth(390);

        //setting buttons
        setDeleteButton();
        setEditButton();
        setJournalPage(titleName);
        setBackButton();

        //setting layout
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
            openEntryLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            backButton2.setBackgroundColor(Color.parseColor("#FFC107"));
            editButton.setBackgroundColor(Color.parseColor("#00E1FF"));
            titleView.setTextColor(Color.parseColor("#000000"));
            dateView.setTextColor(Color.parseColor("#000000"));
            journalView.setTextColor(Color.parseColor("#000000"));
        } else if(file.getString("theme", "none").equals("Night Owl")){
            openEntryLayout.setBackgroundColor(Color.parseColor("#6C6C6C"));
            backButton2.setBackgroundColor(Color.parseColor("#00E1FF"));
            editButton.setBackgroundColor(Color.parseColor("#FFC107"));
            titleView.setTextColor(Color.parseColor("#FFFFFF"));
            dateView.setTextColor(Color.parseColor("#FFFFFF"));
            journalView.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            backButton2.setBackgroundColor(Color.parseColor(getHexColors("backColor", "orange")));
            editButton.setBackgroundColor(Color.parseColor(getHexColors("addEditColor", "blue")));
            titleView.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            dateView.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            journalView.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            openEntryLayout.setBackgroundColor(Color.parseColor(getHexColors("backgroundColor", "white")));
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

    //setting the page activity to show title and other elements
    public void setJournalPage(String title){
        control.open();
        String date = control.getDate(title);
        String journalText = control.getJournal(title);
        control.close();

        titleView.setText(title);
        dateView.setText(date);
        journalView.setText(journalText);
    }

    public void setBackButton(){
        if(editButton.getText().toString().equalsIgnoreCase("Edit")) {
            backButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            });
        } else if(editButton.getText().toString().equalsIgnoreCase("Save")){
            backButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editButton.setText("Edit");
                    journalView.setEnabled(false);
                    titleView.setEnabled(false);

                    setBackButton();
                }
            });
        }
    }

    //set to true if either titleView or journalView is changed
    public boolean isChanged(){
        if(!titleView.getText().toString().equalsIgnoreCase(ogTitle) ||
                !journalView.getText().toString().equalsIgnoreCase(ogEntry)){
            return true;
        }
        return false;
    }

    public void setDeleteButton(){
        deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                control.delete(titleView.getText().toString());
                control.close();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void setEditButton(){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editButton.getText().toString().equalsIgnoreCase("Edit")){
                    editButton.setText("Save");
                    editButton.setBackgroundColor(Color.parseColor("#7CFF73"));
                    journalView.setEnabled(true);
                    titleView.setEnabled(true);

                    setBackButton();

                }else if(editButton.getText().toString().equalsIgnoreCase("Save")){
                    editButton.setText("Edit");
                    editButton.setBackgroundColor(Color.parseColor("#00E1FF"));
                    journalView.setEnabled(false);
                    titleView.setEnabled(false);

                    //updating database
                    if(isChanged()){
                        String newTitle = titleView.getText().toString();
                        String newJournal = journalView.getText().toString();

                        control.open();
                        //getting the date since date is not modified in this activity
                        String month = control.getMonth(ogTitle);
                        String day= control.getDay(ogTitle);
                        String year = control.getYear(ogTitle);

                        control.updateEntry(ogTitle, newTitle, month, day, year, newJournal);
                    }

                    setBackButton();
                }
            }
        });
    }
}