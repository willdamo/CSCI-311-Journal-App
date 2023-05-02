package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    Button backButton2;
    Button editButton;
    Button deleteButton2;
    EditText titleView;
    TextView dateView;
    EditText journalView;
    DatabaseControl control;
    String ogTitle;
    String ogEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_entry);

        Intent i = getIntent();
        String titleName = i.getStringExtra("title");

        control = new DatabaseControl(this);

        ogTitle = titleName;
        control.open();
        ogEntry = control.getJournal(titleName);
        control.close();

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
                    journalView.setEnabled(true);
                    titleView.setEnabled(true);

                    setBackButton();

                }else if(editButton.getText().toString().equalsIgnoreCase("Save")){
                    editButton.setText("Edit");
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