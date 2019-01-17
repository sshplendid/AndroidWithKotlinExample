package com.example.sample_1_with_java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "tododb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String todoSQL="create table tb_todo " +
                "(_id integer primary key autoincrement," +
                "title," +
                "content," +
                "date," +
                "completed)";//0 - none, 1 - completed
        db.execSQL(todoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
