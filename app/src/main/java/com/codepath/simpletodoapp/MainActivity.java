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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<TaskProperty> listOfTaskProperty;
    ArrayAdapter<String> itemsAdapter;
    CustomListView customListView0;
    ListView lvItems;
    SQLiteOpenHelper dbhp0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Todo List");
        //getActionBar().setIcon(R.drawable.my_icon);

        //Load existed data in database and put into view
        try {
            dbhp0 = new dbTodoHelper(this);
            db = dbhp0.getReadableDatabase();
            Cursor cursor = db.query("TODOLIST",
                    new String[] {"TASKNAME", "DUEDATE", "PRIORITY","_id"},
                    null,
                    null,
                    null,null,null
                    );
            listOfTaskProperty = dbToCustomAdapter(cursor);
            lvItems=(ListView)findViewById(R.id.lvItems);
            customListView0 = new CustomListView(this, listOfTaskProperty );
            lvItems.setAdapter(customListView0);
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
                        try {
                            //Toast.makeText(MainActivity.this,"This is: "+ customListView0.getItem(pos).id, Toast.LENGTH_LONG).show();
                            dbhp0 = new dbTodoHelper(MainActivity.this);
                            db = dbhp0.getReadableDatabase();
                            db.delete("TODOLIST", "_id" + "=" + customListView0.getItem(pos).id , null);
                            Cursor cursor = db.query("TODOLIST",
                                    new String[] {"TASKNAME", "DUEDATE", "PRIORITY","_id"},
                                    null,
                                    null,
                                    null,null,null
                            );
                            listOfTaskProperty = dbToCustomAdapter(cursor);
                            customListView0 = new CustomListView(MainActivity.this, listOfTaskProperty );
                            lvItems.setAdapter(customListView0);
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
                //toEditIntent.putExtra("ETASKNA", listItem.taskName);
                toEditIntent.putExtra("EtaskName",listItem.taskName);
                toEditIntent.putExtra("EdueDate",listItem.dueDate);
                toEditIntent.putExtra("Epriority",listItem.priority);
                toEditIntent.putExtra("Eid", listItem.id);
                startActivityForResult(toEditIntent, 1);;
            }
        });
    }


    public void onAddItem(View view) {
        Intent toEditIntent = new Intent(MainActivity.this, EditActivity.class);
        startActivityForResult(toEditIntent, 1);;
    }


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String flag = data.getStringExtra("ESAVE");
        if(requestCode==1) {
            if (flag.equals("save")) {
                Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_LONG).show();
                String taskName = data.getStringExtra("EtaskName");
                String dueDate = data.getStringExtra("EdueDate");
                String priority = data.getStringExtra("Epriority");
                String id = data.getStringExtra("Eid");
                try {
                    dbhp0 = new dbTodoHelper(MainActivity.this);
                    db = dbhp0.getReadableDatabase();
                    ContentValues todoRow = new ContentValues();
                    todoRow.put("TASKNAME", taskName);
                    todoRow.put("DUEDATE", dueDate);
                    todoRow.put("PRIORITY", priority);
                    db.update("TODOLIST", todoRow, "_id=?", new String[] {String.valueOf(id)});
                    Cursor cursor = db.query("TODOLIST",
                            new String[]{"TASKNAME", "DUEDATE", "PRIORITY", "_id"},
                            null,
                            null,
                            null, null, null
                    );
                    listOfTaskProperty = dbToCustomAdapter(cursor);
                    customListView0 = new CustomListView(MainActivity.this, listOfTaskProperty);
                    lvItems.setAdapter(customListView0);
                    db.close();
                    cursor.close();
                } catch (SQLiteException e) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                }
            }
            else {
                if (flag.equals("cancel")) {
                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "new", Toast.LENGTH_LONG).show();
                    String taskName = data.getStringExtra("EtaskName");
                    String dueDate = data.getStringExtra("EdueDate");
                    String priority = data.getStringExtra("Epriority");
                    try {
                        dbhp0 = new dbTodoHelper(MainActivity.this);
                        db = dbhp0.getReadableDatabase();
                        ContentValues todoRow = new ContentValues();
                        todoRow.put("TASKNAME", taskName);
                        todoRow.put("DUEDATE", dueDate);
                        todoRow.put("PRIORITY", priority);
                        db.insert("TODOLIST", null, todoRow);
                        Cursor cursor = db.query("TODOLIST",
                                new String[]{"TASKNAME", "DUEDATE", "PRIORITY", "_id"},
                                null,
                                null,
                                null, null, null
                        );
                        listOfTaskProperty = dbToCustomAdapter(cursor);
                        customListView0 = new CustomListView(MainActivity.this, listOfTaskProperty);
                        lvItems.setAdapter(customListView0);
                        db.close();
                        cursor.close();
                    } catch (SQLiteException e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
