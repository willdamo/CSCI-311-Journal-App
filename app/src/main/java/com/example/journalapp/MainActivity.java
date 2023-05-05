package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    View mainLayout;
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

        mainLayout = findViewById(R.id.mainLayout);
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
        SharedPreferences settingsFile = getSharedPreferences("settings", Context.MODE_PRIVATE);
        setSettings(settingsFile);

        //setting the layout
        setLayout();

        System.out.println("textColor: "+ settingsFile.getString("textColor", "none"));
    }

    public void setLayout(){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if(file.contains("changed")){
            journalName.setText(file.getString("journalName", "My Journal"));
            if(file.getString("theme", "n/a").equals("Default")){
                mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                deleteButton.setBackgroundColor(Color.parseColor("#FF6969"));
                journalName.setTextColor(Color.parseColor("#000000"));
                newEntryButton.setBackgroundColor(Color.parseColor("#00E1FF"));
            } else if(file.getString("theme", "none").equals("Night Owl")){
                mainLayout.setBackgroundColor(Color.parseColor("#6C6C6C"));
                journalName.setTextColor(Color.parseColor("#FFFFFF"));
                selectedView.setTextColor(Color.parseColor("#FFFFFF"));
                newEntryButton.setBackgroundColor(Color.parseColor("#FFC107"));
            }else {
                newEntryButton.setBackgroundColor(Color.parseColor(getHexColors("addEditColor", "blue")));
                journalName.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
                selectedView.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
                mainLayout.setBackgroundColor(Color.parseColor(getHexColors("backgroundColor", "white")));
            }
        }
    }

    public String getHexColors(String pref, String err){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String colorName = file.getString(pref, err);
        String colorHex = "";

        if(colorName.equalsIgnoreCase("Default")){
            colorName = err;
        }

        if(colorName.equalsIgnoreCase("white")){
            colorHex = "#FFFFFF";
        } else if(colorName.equalsIgnoreCase("black")){
            colorHex = "#000000";
        }else if(colorName.equalsIgnoreCase("red")){
            colorHex = "#FF6969";
        }else if(colorName.equalsIgnoreCase("blue")){
            colorHex = "#00E1FF";
        }else if(colorName.equalsIgnoreCase("purple")){
            colorHex = "#CE74FF";
        }else if(colorName.equalsIgnoreCase("orange")){
            colorHex = "#FFC107";
        }else if(colorName.equalsIgnoreCase("gray")){
            colorHex = "#989898";
        }else if(colorName.equalsIgnoreCase("dark gray")){
            colorHex = "#6C6C6C";
        }
        return colorHex;
    }

    protected void onResume() {
        super.onResume();
        setRecyclerView();
        setLayout();
    }

    //sets the default values in shared preferences
    public void setSettings(SharedPreferences file) {
        SharedPreferences.Editor editor = file.edit();
        if(file.contains("changed")){
            return;
        }else{
            editor.putString("journalName", journalName.getText().toString());
            editor.putString("theme", "None");
            editor.putString("backColor", "Default");
            editor.putString("addEditColor", "Default");
            editor.putString("textColor",  "Black");
            editor.putString("backgroundColor",  "Default");
            editor.commit();
        }
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
        adapter = new CustomAdapter(control.getTitles(), control.getDates(), this);
        control.close();

        if(!(adapter == null)) {
            adapter.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedTitle = ((TextView) view).getText().toString();
                    openButton.setVisibility(openButton.VISIBLE);
                    deleteButton.setVisibility(deleteButton.VISIBLE);
                    selectedView.setText(selectedTitle+ " selected");
                }
            });
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}