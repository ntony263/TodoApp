package com.codepath.simpletodoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<TaskProperty> threeStringsList;
    ArrayAdapter<String> itemsAdapter;
    CustomListView threeHorizontalTextViewsAdapter;
    ListView lvItems;
    SQLiteOpenHelper dbhp0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*readItems();
        //Toast.makeText(this,items.get(0), Toast.LENGTH_LONG).show();
        lvItems=(ListView)findViewById(R.id.lvItems);
        //items= new ArrayList<>();
        itemsAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);*/
        try {
            //Toast.makeText(this,"DB!!!!", Toast.LENGTH_LONG).show();
            dbhp0 = new dbTodoHelper(this);
            db = dbhp0.getReadableDatabase();
            Cursor cursor = db.query("TODOLIST",
                    new String[] {"TASKNAME", "DUEDATE", "PRIORITY","_id"},
                    null,
                    null,
                    null,null,null
                    );

            //ArrayList<TaskProperty> threeStringsList = new ArrayList<>();
            threeStringsList = dbToCustomAdapter(cursor);
            lvItems=(ListView)findViewById(R.id.lvItems);
            threeHorizontalTextViewsAdapter = new CustomListView(this, threeStringsList );

            lvItems.setAdapter(threeHorizontalTextViewsAdapter);
            cursor.close();
            db.close();
        }
        catch (SQLiteException e){
            Toast.makeText(this,"error", Toast.LENGTH_LONG).show();
        }

        setupListViewListener();
    }



    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                        /*items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();*/
                        try {
                            Toast.makeText(MainActivity.this,"This is: "+ threeHorizontalTextViewsAdapter.getItem(pos).id, Toast.LENGTH_LONG).show();
                            dbhp0 = new dbTodoHelper(MainActivity.this);
                            db = dbhp0.getReadableDatabase();
                            db.delete("TODOLIST", "_id" + "=" + threeHorizontalTextViewsAdapter.getItem(pos).id , null);
                            Cursor cursor = db.query("TODOLIST",
                                    new String[] {"TASKNAME", "DUEDATE", "PRIORITY","_id"},
                                    null,
                                    null,
                                    null,null,null
                            );
                            threeStringsList = dbToCustomAdapter(cursor);
                            threeHorizontalTextViewsAdapter = new CustomListView(MainActivity.this, threeStringsList );
                            lvItems.setAdapter(threeHorizontalTextViewsAdapter);
                            db.close();
                            cursor.close();
                        }
                        catch (SQLiteException e){
                            Toast.makeText(MainActivity.this,"error", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int position, long id) {
                TaskProperty listItem =(TaskProperty) lvItems.getItemAtPosition(position);
                Intent toEditIntent = new Intent(MainActivity.this, EditActivity.class);
                toEditIntent.putExtra("data", listItem.taskName);
                toEditIntent.putExtra("id", id);
                startActivityForResult(toEditIntent, 1);;
            }
        });
    }


    public void onAddItem(View view) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        /*itemsAdapter.add(itemText);
        etNewItem.setText("");*/

        //writeItems();
    }

    /*private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            //items = new ArrayList<String>(FileUtils.readLines(todoFile));
            //Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            //items = new ArrayList<String>();
        }
    }*/

   /* private void writeItems (){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }*/

    private ArrayList<TaskProperty> dbToCustomAdapter (Cursor cursor){
        ArrayList<TaskProperty> forReturn = new ArrayList<>();
        if (cursor.moveToFirst()){
            TaskProperty currentValue = new TaskProperty(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            forReturn.add(currentValue);
            while (cursor.moveToNext()){
                currentValue = new TaskProperty(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                forReturn.add(currentValue);
            }
        }
        return forReturn;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {

            String taskName=data.getStringExtra("EtaskName");
            String dueDate=data.getStringExtra("EdueDate");
            String priority=data.getStringExtra("Epriority");
            try {
                dbhp0 = new dbTodoHelper(MainActivity.this);
                db = dbhp0.getReadableDatabase();
                ContentValues todoRow = new ContentValues();
                todoRow.put("TASKNAME", taskName);
                todoRow.put("DUEDATE", dueDate);
                todoRow.put("PRIORITY", priority);
                db.insert("TODOLIST",null, todoRow);
                Cursor cursor = db.query("TODOLIST",
                        new String[] {"TASKNAME", "DUEDATE", "PRIORITY","_id"},
                        null,
                        null,
                        null,null,null
                );
                threeStringsList = dbToCustomAdapter(cursor);
                threeHorizontalTextViewsAdapter = new CustomListView(MainActivity.this, threeStringsList );
                lvItems.setAdapter(threeHorizontalTextViewsAdapter);
                db.close();
                cursor.close();
            }
            catch (SQLiteException e){
                Toast.makeText(MainActivity.this,"error", Toast.LENGTH_LONG).show();
            }

            /*dbhp0 = new dbTodoHelper(MainActivity.this);
            db = dbhp0.getReadableDatabase();
            ContentValues todoRow = new ContentValues();
            todoRow.put("TASKNAME", taskName);
            todoRow.put("DUEDATE", dueDate);
            todoRow.put("PRIORITY", priority);
            db.insert("TODOLIST",null, todoRow);*/
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //readItems();
        //itemsAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        //lvItems.setAdapter(itemsAdapter);
        //Toast.makeText(getApplicationContext(),itemsAdapter.getItem(1), Toast.LENGTH_LONG).show();
    }
}
