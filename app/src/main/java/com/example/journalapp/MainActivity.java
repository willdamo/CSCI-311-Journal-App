package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/*
TODO LIST
Must add a settings feature that gives a way to the configurations of the text size, button colors
and etc.
Add settings activity
Configure settings button
Settings will also have the option to delete everything from the database.
 */
public class MainActivity extends AppCompatActivity {

    TextView journalName;
    ImageButton newEntryButton;
    Button openButton;
    Button deleteButton;
    ImageButton settingsButton;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    DatabaseControlEntries control;
    String selectedTitle = "";
    TextView selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControlEntries(this);

        journalName = findViewById(R.id.journalName);
        newEntryButton = findViewById(R.id.newEntryButton);
        openButton = findViewById(R.id.openButton);
        deleteButton = findViewById(R.id.deleteButton);
        settingsButton =findViewById(R.id.settingsButton);
        recyclerView = findViewById(R.id.recyclerView);
        selectedView = findViewById(R.id.selectedView);

        openButton.setVisibility(openButton.INVISIBLE);
        deleteButton.setVisibility(deleteButton.INVISIBLE);

        //setting buttons
        setNewEntryButton();
        setDeleteButton();
        setOpenButton();
        setSettingsButton();

        //setting default settings
        setDefaultSettings();
    }

    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    //sets the default values in shared preferences
    public void setDefaultSettings() {
        SharedPreferences settingsFile = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settingsFile.edit();
        editor.putString("journalName", journalName.getText().toString());
        editor.putString("theme", "Default");
        editor.putString("saveColor", "Default");
        editor.putString("deleteColor", "Default");
        editor.putString("backColor", "Default");
        editor.putString("addEditColor", "Default");
        editor.putString("textColor", "Default");
        editor.putString("backgroundColor", "Default");
    }

    public void applyUpdatedSettings(){

    }

    public void setNewEntryButton(){
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewEntryActivity.class);
                startActivity(i);
            }
        });
    }

    public void setSettingsButton(){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                i.putExtra("journalName", journalName.getText().toString());
                startActivity(i);
            }
        });
    }

    public void setDeleteButton(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTitle.equals("")){
                    return;
                }
                control.open();
                control.delete(selectedTitle);
                control.close();
                onResume();
                Toast.makeText(getApplicationContext(), selectedTitle+" DELETED Successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setOpenButton(){
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTitle.equals("")){
                    return;
                }
                Intent i = new Intent(getApplicationContext(), openEntryActivity.class);
                i.putExtra("title", selectedTitle);
                startActivity(i);
            }
        });
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
                    openButton.setVisibility(openButton.VISIBLE);
                    deleteButton.setVisibility(deleteButton.VISIBLE);
                    selectedView.setText(selectedTitle+" selected");

                }
            });
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}