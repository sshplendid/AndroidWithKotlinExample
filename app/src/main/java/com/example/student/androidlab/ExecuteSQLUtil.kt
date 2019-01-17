package com.example.student.androidlab

import android.content.ContentValues
import android.content.Context

// ExecuteSQLUtil
// contentValues: insert data Map같은 객체
fun insertTodo(context: Context, values: ContentValues) {
    val helper = DBHelper(context)
    val db = helper.writableDatabase // java에선 getWritableDatabase()이지만, 코틀린에선 프로퍼티로 이용 가능하다.

    db.insert("tb_todo", null, values)
    db.close()
}

fun selectTodos(context: Context): MutableList<TodoVO> {
    val result = mutableListOf<TodoVO>()

    val helper = DBHelper(context)
    val db = helper.readableDatabase
    val cursor = db.rawQuery("""select * from tb_todo
        |order by date desc
    """.trimMargin(), null)

    while(cursor.moveToNext()) {
        cursor.run {
            val vo = TodoVO(
                    getInt(0),
                    getString(1),
                    getString(2),
                    getString(3),
                    getInt(4)
                    )
            result.add(vo)
        }
    }
    db.close()
    return result
}