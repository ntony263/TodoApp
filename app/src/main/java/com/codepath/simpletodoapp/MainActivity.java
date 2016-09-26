package com.codepath.simpletodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        //Toast.makeText(this,items.get(0), Toast.LENGTH_LONG).show();
        lvItems=(ListView)findViewById(R.id.lvItems);
        //items= new ArrayList<>();
        itemsAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //readItems();
        //Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_LONG).show();
        //items.add("First Item");
        //items.add("Second Item");
        //Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this,items.get(1), Toast.LENGTH_LONG).show();
        //setupListViewLongListener();
        setupListViewListener();
    }

    /*private void setupListViewLongListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }*/

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int position, long id) {
                String listItem = (String)lvItems.getItemAtPosition(position);
                Intent toEditIntent = new Intent(MainActivity.this, EditActivity.class);
                toEditIntent.putExtra("data", listItem);
                toEditIntent.putExtra("id", id);
                startActivity(toEditIntent);
            }
        });
    }


    public void onAddItem(View view) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            //Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems (){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        readItems();
        itemsAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //Toast.makeText(getApplicationContext(),itemsAdapter.getItem(1), Toast.LENGTH_LONG).show();
    }
}
