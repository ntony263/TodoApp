package com.codepath.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit");

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
        //Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();
        if (id != null){
            intent.putExtra("Eid",id);
            intent.putExtra("ESAVE","save");
        }
        else {
            intent.putExtra("ESAVE","create");
        }

        setResult(1,intent);
        finish();
    }

    public void toCancel(View view) {
        //Toast.makeText(getApplicationContext(),"Change dimissed!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.putExtra("ESAVE","cancel");
        setResult(1,intent);
        finish();
    }
}
