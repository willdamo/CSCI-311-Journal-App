package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewEntryActivity extends AppCompatActivity {

    EditText titleInput;
    Spinner monthSpinner;
    EditText dayInput;
    EditText yearInput;
    EditText entryInput;
    Button backButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        titleInput = findViewById(R.id.titleInput);
        monthSpinner = findViewById(R.id.monthSpinner);
        dayInput = findViewById(R.id.dayInput);
        yearInput = findViewById(R.id.yearInput);
        entryInput = findViewById(R.id.entryInput);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);

        setBackButton();
        setMonthSpinner();
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
}