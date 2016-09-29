package com.codepath.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    ArrayList<String> items;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //readItems();
        String eTaskName = getIntent().getStringExtra("EtaskName");
        String edueDate = getIntent().getStringExtra("EdueDate");
        String ePriority = getIntent().getStringExtra("Epriority");
        id = getIntent().getStringExtra("Eid");
        EditText etNewItem0 = (EditText)findViewById(R.id.etEdit0);
        EditText etNewItem1 = (EditText)findViewById(R.id.etEdit1);
        EditText etNewItem2 = (EditText)findViewById(R.id.etEdit2);
        if (eTaskName != null){
            etNewItem0.append(eTaskName);
        }
        else {
            etNewItem0.append("");
        }
        if (edueDate != null){
            etNewItem1.append(edueDate);
        }
        else {
            etNewItem1.append("");
        }
        if (ePriority != null){
            etNewItem2.append(ePriority);
        }
        else {
            etNewItem2.append("");
        }
    }

    public void onSaveItem(View view) {
        EditText etNewItem0 = (EditText)findViewById(R.id.etEdit0);
        EditText etNewItem1 = (EditText)findViewById(R.id.etEdit1);
        EditText etNewItem2 = (EditText)findViewById(R.id.etEdit2);
        //String newValue = etNewItem.getText().toString();
        String eTaskName = etNewItem0.getText().toString();
        String edueDate = etNewItem1.getText().toString();
        String ePriority = etNewItem2.getText().toString();
        //
        Intent intent=new Intent();
        intent.putExtra("EtaskName",eTaskName);
        intent.putExtra("EdueDate",edueDate);
        intent.putExtra("Epriority",ePriority);
        intent.putExtra("Eid",id);
        intent.putExtra("ESAVE","save");
        setResult(1,intent);

        //items.set((int)id,newValue);
       // writeItems();
        Toast.makeText(getApplicationContext(),"Saved!", Toast.LENGTH_LONG).show();
        finish();
        //Intent toMainIntent = new Intent(this, MainActivity.class);
        //startActivity(toMainIntent);
    }

    /*private void writeItems (){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        //Toast.makeText(this,filesDir.toString(),Toast.LENGTH_LONG).show();
        try {
            items = new ArrayList<String> (FileUtils.readLines(todoFile));
        }
        catch (IOException e){
            items = new ArrayList<String>();
        }
    }*/


    public void toCancel(View view) {
        Toast.makeText(getApplicationContext(),"Change dimissed!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.putExtra("ESAVE","cancel");
        setResult(1,intent);
        finish();
    }
}
