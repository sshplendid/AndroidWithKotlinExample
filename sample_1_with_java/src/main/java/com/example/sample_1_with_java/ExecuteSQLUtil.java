package com.example.sample_1_with_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ExecuteSQLUtil {
    public static void insertTodo(Context context, ContentValues values){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert("tb_todo", null, values);
        db.close();
    }
    public static ArrayList<TodoVO> selectTodos(Context context){
        ArrayList<TodoVO> results = new ArrayList<>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_todo order by date desc", null);
        while (cursor.moveToNext()){
            TodoVO vo = new TodoVO();
            vo.id = cursor.getInt(0);
            vo.title = cursor.getString(1);
            vo.content = cursor.getString(2);
            vo.date = cursor.getString(3);
            vo.completed = cursor.getInt(4);
            results.add(vo);
        }
        db.close();
        return results;
    }

    public static void updateTodo(Context context, ContentValues values, String where){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update("tb_todo", values, where, null);
        db.close();
    }

}
