package com.codepath.simpletodoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<TaskProperty> {
    public CustomListView(Context context, ArrayList<TaskProperty> listOfTask){
        super(context,0, listOfTask);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TaskProperty taskProperty = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_view, parent, false);
        }

        // Lookup view for data population
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvCustom0);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvCustom2);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvCustom1);

        // Put the data into the template view
        tvTaskName.setText(taskProperty.getTaskName());
        tvPriority.setText(taskProperty.getPriority());
        if (taskProperty.getPriority().equals("High")){
            tvPriority.setTextColor(Color.parseColor("#ff0000"));
        }
        tvDueDate.setText(taskProperty.getDueDate());
        // Return the completed view to render on screen
        return convertView;
    }
}
