package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    TextView settingsText;
    TextView setThemeText;
    TextView backColorText;
    TextView addEditText;
    TextView textColorText;
    TextView backgroundText;

    View settingsLayOut;
    String ogTitle;
    EditText changeJournalName;
    Spinner themeSpinner;
    Spinner backColorSpinner;
    Spinner addEditColorSpinner;
    Spinner textColorSpinner;
    Spinner backgroundColorSpinner;
    Button backButton;
    Button saveButton;
    Button deleteAllButton;
    DatabaseControlEntries control;

    String newTitle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        control = new DatabaseControlEntries(this);

        Intent i = getIntent();
        ogTitle = i.getStringExtra("journalName");

        //initializing textViews
        settingsText = findViewById(R.id.settingsText);
        setThemeText = findViewById(R.id.setThemeText);
        backColorText = findViewById(R.id.backColorText);
        addEditText = findViewById(R.id.addEditColorText);
        textColorText = findViewById(R.id.textColorText);
        backgroundText = findViewById(R.id.backgroundText);

        //initializing the spinners and edit text and buttons and layout
        settingsLayOut = findViewById(R.id.settingsLayout);
        changeJournalName = findViewById(R.id.changeJournalName);

        backButton = findViewById(R.id.settingsBackButton);
        saveButton = findViewById(R.id.settingsSaveButton);
        deleteAllButton = findViewById(R.id.deleteAllButton);

        themeSpinner = findViewById(R.id.themeSpinner);
        backColorSpinner = findViewById(R.id.backColorSpinner);
        addEditColorSpinner = findViewById(R.id.addEditColorSpinner);
        textColorSpinner = findViewById(R.id.textColorSpinner);
        backgroundColorSpinner = findViewById(R.id.backgroundColorSpinner);

        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        //setting buttons
        setBackButton();
        setSaveButton();
        setDeleteAllButton();

        //sets all the spinners
        setColorSpinners();
        setThemeSpinner();

        setLayout();
    }

    private void setDeleteAllButton() {
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                control.deleteAll();
                control.close();
                Toast.makeText(getApplicationContext(), "ALL ENTRIES DELETED", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setLayout(){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        //checks inside theme, if theme is none then proceed to make changes to other configurations
        if(file.getString("theme", "None").equals("Default")){
            settingsLayOut.setBackgroundColor(Color.parseColor("#FFFFFF"));
            backButton.setBackgroundColor(Color.parseColor("#FFC107"));
            saveButton.setBackgroundColor(Color.parseColor("#7CFF73"));
            deleteAllButton.setBackgroundColor(Color.parseColor("#FF6969"));
            settingsText.setTextColor(Color.parseColor("#000000"));
            setThemeText.setTextColor(Color.parseColor("#000000"));
            backColorText.setTextColor(Color.parseColor("#000000"));
            addEditText.setTextColor(Color.parseColor("#000000"));
            textColorText.setTextColor(Color.parseColor("#000000"));
            backgroundText.setTextColor(Color.parseColor("#000000"));

            editor.putString("backColor", "Default");
            editor.putString("addEditColor", "Default");
            editor.putString("textColor",  "Default");
            editor.putString("backgroundColor",  "Default");
            editor.apply();

        } else if(file.getString("theme", "None").equals("Night Owl")){
            settingsLayOut.setBackgroundColor(Color.parseColor("#6C6C6C"));
            backButton.setBackgroundColor(Color.parseColor("#00E1FF"));
            settingsText.setTextColor(Color.parseColor("#FFFFFF"));
            setThemeText.setTextColor(Color.parseColor("#FFFFFF"));
            backColorText.setTextColor(Color.parseColor("#FFFFFF"));
            addEditText.setTextColor(Color.parseColor("#FFFFFF"));
            textColorText.setTextColor(Color.parseColor("#FFFFFF"));
            backgroundText.setTextColor(Color.parseColor("#FFFFFF"));

            editor.putString("backColor", "Default");
            editor.putString("addEditColor", "Default");
            editor.putString("textColor",  "Default");
            editor.putString("backgroundColor",  "Default");
            editor.apply();

        }else if(!file.getString("theme", "None").equals("Default") && !file.getString("theme", "none").equals("Night Owl")){
            backButton.setBackgroundColor(Color.parseColor(getHexColors("backColor", "orange")));
            settingsText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            setThemeText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            backColorText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            addEditText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            textColorText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            backgroundText.setTextColor(Color.parseColor(getHexColors("textColor", "black")));
            settingsLayOut.setBackgroundColor(Color.parseColor(getHexColors("backgroundColor", "white")));
        }else {
            return;
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

    @Override
    protected void onResume() {
        super.onResume();
        setLayout();
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
                boolean change = false;
                int[] cArr = isChanged();
                for(int c : cArr){
                    if(c == 1){
                        change = true;
                    }
                }

                if(change) {
                    updateSettingsFile(cArr);
                    onResume();
                }
            }
        });
    }

    public void updateSettingsFile(int[] arr){
        boolean change = false;
        for(int c : arr){
            if(c == 1){
                change = true;
            }
        }
        
        if(change){
            SharedPreferences settingsFile = getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settingsFile.edit();

            TextView theme = ((TextView) themeSpinner.getSelectedView());
            TextView backColor = ((TextView) backColorSpinner.getSelectedView());
            TextView addEditColor = ((TextView) addEditColorSpinner.getSelectedView());
            TextView textColor = ((TextView) textColorSpinner.getSelectedView());
            TextView backgroundColor = ((TextView) backgroundColorSpinner.getSelectedView());

            String newTheme = theme.getText().toString();
            String newBackcolor = backColor.getText().toString();
            String newAddEditColor = addEditColor.getText().toString();
            String newTextColor = textColor.getText().toString();
            String newBackgroundColor = backgroundColor.getText().toString();

            //applies to settings if a theme is changed
            //if not changed then change the other configurations
            //this is to counteract overlapping between themes and configurations, cannot do both
            if(!settingsFile.getString("theme", "None").equalsIgnoreCase("None") && (arr[1] == 1 || arr[2] == 1 || arr[3] == 1 || arr[4] == 1)){
                Toast.makeText(getApplicationContext(), "Themes take precedent!", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Set theme to 'None' for custom configuration", Toast.LENGTH_LONG).show();
                return;
            }

            if (arr[5] == 1) {
                String newJournal = changeJournalName.getText().toString();
                editor.putString("journalName", newJournal);
            }
            if(arr[0] == 1) {
                editor.putString("theme", newTheme);
            }
            //will look for any changes in the following areas and applies them to the settings file
            if(arr[1] == 1) {
                editor.putString("backColor", newBackcolor);
            }
            if(arr[2] == 1) {
                editor.putString("addEditColor", newAddEditColor);
            }
            if(arr[3] == 1) {
                editor.putString("textColor", newTextColor);
            }
            if(arr[4] == 1) {
                editor.putString("backgroundColor", newBackgroundColor);
            }

            editor.apply();
            //will mark settings as changed
            editor.putString("changed", "");
            editor.commit();

            if(settingsFile.getString("theme", "none").equalsIgnoreCase("Default")){
                Toast.makeText(getApplicationContext(), "Default Theme Enabled!", Toast.LENGTH_LONG).show();
            }else if(settingsFile.getString("theme", "none").equalsIgnoreCase("Night Owl")){
                Toast.makeText(getApplicationContext(), "Night Owl Theme Enabled!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Custom Theme Enabled!", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Returns the change number if anything in the screen is changed
    public int[] isChanged(){
        SharedPreferences file = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int[] changeArr = {0,0,0,0,0,0};
        //Sets the text view for later usage
        TextView theme = (TextView) themeSpinner.getSelectedView();
        TextView back = (TextView) backColorSpinner.getSelectedView();
        TextView addEdit = (TextView) addEditColorSpinner.getSelectedView();
        TextView text = (TextView) textColorSpinner.getSelectedView();
        TextView background = (TextView) backgroundColorSpinner.getSelectedView();

        //index 0
        if(theme != null && !theme.getText().toString().equals(file.getString("theme", "None"))){
            changeArr[0]++;
        }
        //index 1
        if(back != null && !back.getText().toString().equals("Default")){
            changeArr[1]++;
        }
        //index 2
        if(addEdit != null && !addEdit.getText().toString().equals("Default")){
            changeArr[2]++;
        }
        //index 3
        if(text != null && !text.getText().toString().equals("Default")){
            changeArr[3]++;
        }
        //index 4
        if(background != null && !background.getText().toString().equals("Default")){
            changeArr[4]++;
        }
        //index 5
        if(!TextUtils.isEmpty(changeJournalName.getText().toString()) &&
                !ogTitle.equalsIgnoreCase(changeJournalName.getText().toString())) {
            changeArr[5]++;
        }
        return changeArr;
    }

    //sets the color spinners
    public void setColorSpinners(){
        setColorSpinner(backColorSpinner);
        setColorSpinner(addEditColorSpinner);
        setColorSpinner(backgroundColorSpinner);
        setColorSpinner(textColorSpinner);
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