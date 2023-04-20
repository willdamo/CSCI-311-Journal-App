package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button newEntryButton;
    Button openButton;
    Button deleteButton;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    DatabaseControl control;
    String selectedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControl(this);

        newEntryButton = findViewById(R.id.newEntryButton);
        openButton = findViewById(R.id.openButton);
        deleteButton = findViewById(R.id.deleteButton);
        recyclerView = findViewById(R.id.recyclerView);

        setNewEntry();
        setDeleteButton();
    }

    public void setNewEntry(){
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewEntryActivity.class);
                startActivity(i);
            }
        });
    }

    public void setDeleteButton(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                control.delete(selectedTitle);
                control.close();
                onResume();
                Toast.makeText(getApplicationContext(), selectedTitle+" DELETED Successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }


    public void setRecyclerView(){
        control.open();
        adapter = new CustomAdapter(control.getTitles());
        control.close();

        if(!(adapter == null)) {
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedTitle = ((TextView) view).getText().toString();
                    Toast.makeText(getApplicationContext(), selectedTitle +" selected",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}