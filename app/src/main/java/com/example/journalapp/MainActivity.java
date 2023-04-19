package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newEntryButton;
    Button openButton;
    Button deleteButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newEntryButton = findViewById(R.id.newEntryButton);
        openButton = findViewById(R.id.openButton);
        deleteButton = findViewById(R.id.deleteButton);
        recyclerView = findViewById(R.id.recyclerView);

        newEntry();
    }

    public void newEntry(){
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewEntryActivity.class);
                startActivity(i);
            }
        });
    }
}