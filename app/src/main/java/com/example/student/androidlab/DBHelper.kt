package com.example.student.androidlab

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DBHelper
// file db, todoDB라는 file에 table 여러개
class DBHelper(context: Context): SQLiteOpenHelper(context, "tododb", null, 1) {
    // app install 후 최초 한번 호출
    override fun onCreate(db: SQLiteDatabase) {
        val sql = """create table tb_todo (
            _id integer primary key autoincrement,
            title,
            content,
            date,
            completed)
            """
        db.execSQL(sql.trimMargin())
    }

    // 상위 클래스에 전달되는 db version 정보가 변경될 때마다
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
