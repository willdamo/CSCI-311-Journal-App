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
Must keep original title and journal text intact with String variable
Must be able to update the database by implementing the update method
 */
public class openEntryActivity extends AppCompatActivity {

    Button backButton2;
    Button editButton;
    Button deleteButton2;
    EditText titleView;
    TextView dateView;
    EditText journalView;
    DatabaseControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_entry);

        Intent i = getIntent();
        String titleName = i.getStringExtra("title");

        control = new DatabaseControl(this);

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

                    //add code here to update database
                    setBackButton();
                }
            }
        });
    }
}