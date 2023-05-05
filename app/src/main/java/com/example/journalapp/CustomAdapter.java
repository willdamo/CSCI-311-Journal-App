package com.example.journalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] localDataSet, dateDataSet;
    private View.OnClickListener listener;
    private Context context;

    public CustomAdapter(Context context){
        this.context = context;
    }
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView journalView;
        private final TextView dateView;

        public ViewHolder(View view){
            super(view);

            SharedPreferences file = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

            dateView = (TextView) view.findViewById(R.id.journalDate);
            journalView = (TextView) view.findViewById(R.id.journalName);
            journalView.setOnClickListener(listener);
            journalView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            if(file.contains("changed")){
                if(file.getString("theme", "n/a").equals("Default")){
                    journalView.setTextColor(Color.parseColor("#000000"));
                    dateView.setTextColor(Color.parseColor("#000000"));
                } else if(file.getString("theme", "none").equals("Night Owl")){
                    journalView.setTextColor(Color.parseColor("#FFFFFF"));
                    dateView.setTextColor(Color.parseColor("#FFFFFF"));
                }else {
                    String colorName = file.getString("colorText", "black");
                    String colorHex;
                    if(colorName.equalsIgnoreCase("white")){
                         colorHex = "#FFFFFF";
                    }
                    if(colorName.equalsIgnoreCase("black")){
                        colorHex = "#000000";
                    }
                    if(colorName.equalsIgnoreCase("red")){
                        colorHex = "#FF6969";
                    }
                    if(colorName.equalsIgnoreCase("blue")){
                        colorHex = "#00E1FF";
                    }
                    if(colorName.equalsIgnoreCase("purple")){
                        colorHex = "#CE74FF";
                    }
                    if(colorName.equalsIgnoreCase("orange")){
                        colorHex = "#FFC107";
                    }
                    if(colorName.equalsIgnoreCase("gray")){
                        colorHex = "#989898";
                    }
                    if(colorName.equalsIgnoreCase("dark gray")){
                        colorHex = "#6C6C6C";
                    }else{
                        colorHex = "#000000";
                    }

                    journalView.setTextColor(Color.parseColor(colorHex));
                    dateView.setTextColor(Color.parseColor(colorHex));
                }
            }
        }

        public TextView getTextView(){
            return journalView;
        }
        public TextView getDateView(){
            return dateView;
        }
    }

    public CustomAdapter(String[] dataSet, String[] dateSet, Context context){
        localDataSet = dataSet;
        dateDataSet = dateSet;
        this.context = context;
    }

    //Creates a new ViewHolder, initializes with associated ViewGroup and ViewType
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);

        SharedPreferences file = this.context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if(file.contains("changed")) {
            if (file.getString("theme", "n/a").equals("Default")) {
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else if (file.getString("theme", "none").equals("Night Owl")) {
                view.setBackgroundColor(Color.parseColor("#6C6C6C"));
            } else {
                String colorName = file.getString("colorText", "black");
                String colorHex;
                if (colorName.equalsIgnoreCase("white")) {
                    colorHex = "#FFFFFF";
                }
                if (colorName.equalsIgnoreCase("black")) {
                    colorHex = "#000000";
                }
                if (colorName.equalsIgnoreCase("red")) {
                    colorHex = "#FF6969";
                }
                if (colorName.equalsIgnoreCase("blue")) {
                    colorHex = "#00E1FF";
                }
                if (colorName.equalsIgnoreCase("purple")) {
                    colorHex = "#CE74FF";
                }
                if (colorName.equalsIgnoreCase("orange")) {
                    colorHex = "#FFC107";
                }
                if (colorName.equalsIgnoreCase("gray")) {
                    colorHex = "#989898";
                }
                if (colorName.equalsIgnoreCase("dark gray")) {
                    colorHex = "#6C6C6C";
                } else {
                    colorHex = "#FFFFFF";
                }
                view.setBackgroundColor(Color.parseColor(colorHex));
            }
        }
        return new ViewHolder(view);
    }

    //associates ViewHolder with data
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet[position]);
        viewHolder.getDateView().setText(dateDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
