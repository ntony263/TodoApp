package com.codepath.simpletodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbTodoHelper extends SQLiteOpenHelper{
    private static final String NAME_DB_TODO = "dbTodo0";
    private static final int VERSION_DB_TODO = 1;
    private static final String DATABASE_TABLE = "TODOLIST";

    dbTodoHelper (Context context){
        super(context, NAME_DB_TODO, null, VERSION_DB_TODO);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE " +DATABASE_TABLE+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TASKNAME TEXT,"
                + "DUEDATE NUMERIC,"
                + "PRIORITY TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    @Override
    public void onOpen (SQLiteDatabase db){
        /*insertTask(db,"This is database 1","27/9/2016","high");
        insertTask(db,"This is database 2","30/9/2016","low");
        insertTask(db,"This is database 3","01/10/2016","low");
        insertTask(db,"This is database 4","01/10/2016","low");*/
        //deleteTitle(db, "4");
    }

    private static void insertTask (SQLiteDatabase db, String taskName, String dueDate, String priority){
        ContentValues todoRow = new ContentValues();
        todoRow.put("TASKNAME", taskName);
        todoRow.put("DUEDATE", dueDate);
        todoRow.put("PRIORITY", priority);
        db.insert("TODOLIST",null, todoRow);
    }

    private static boolean deleteTitle(SQLiteDatabase db, String name)
    {
        return db.delete(DATABASE_TABLE, "_id" + "=" + name, null) > 0;
    }
}
