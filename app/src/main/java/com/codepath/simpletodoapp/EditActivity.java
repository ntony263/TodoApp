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
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //readItems();
        String extra = getIntent().getStringExtra("data");
       // id = getIntent().getLongExtra("id",0);
        if (extra != null){
            //Toast.makeText(getApplicationContext(), extra, Toast.LENGTH_LONG).show();
            EditText etNewItem = (EditText)findViewById(R.id.etEdit0);
            //etNewItem.setText(extra);
            etNewItem.append(extra);
        }
    }

    public void onSaveItem(View view) {
        EditText etNewItem = (EditText)findViewById(R.id.etEdit0);
        String newValue = etNewItem.getText().toString();
        //
        Intent intent=new Intent();
        intent.putExtra("EtaskName",newValue);
        intent.putExtra("EdueDate","29/9/2016");
        intent.putExtra("Epriority","high");
        setResult(1,intent);

        //items.set((int)id,newValue);
       // writeItems();
        Toast.makeText(getApplicationContext(),"Saved!", Toast.LENGTH_LONG).show();
        finish();
        //Intent toMainIntent = new Intent(this, MainActivity.class);
        //startActivity(toMainIntent);
    }

    private void writeItems (){
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
    }


    public void toCancel(View view) {
        Toast.makeText(getApplicationContext(),"Change dimissed!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        setResult(1,intent);
        finish();
    }
}
