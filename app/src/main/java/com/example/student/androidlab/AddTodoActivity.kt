package com.example.student.androidlab

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.*

class AddTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        // xml 정의대로 객체가 생성되어 화면에 출력
        // 화면 출력 객체(ex: button)을 프로그램에서 이용하려면
        // findViewById()로 획득해서 이용해야 함

        /*
        DI 개념으로 내가 필요한 view 객체를 주입 => butterknife (Android DI f/w) JAVA???
        findViewById()는 필요 없음
        변수는 10개 선언하고 annotation 추가하고
        */

        /*
        코틀린으로 작성하면 view 획득을 위한 코드가 필요없다/
        kotlin android extension!
        단, import문에 받고자하는 xml을 명시해야 함!!!!!!
         */

        addDateView.text = dateFormat(Date())

        /*
        event 추가
        매개변수에 자바 인터페이스를 구현한 객체
        SAM 기능을 이용해
        람다함수는 추상함수에서 실행할 로직
         */

        addDateView.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, monthOfYear, dayOfMonth ->
                addDateView.text = "$year-${monthOfYear + 1}-${dayOfMonth}"
            }, year, month, day).show()
        }

    }

    // title bar (action bar)의 메뉴 구성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)

    }

    // menu event 저장버튼 눌렀을 때 todo data db 저장
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_add) {
           if(addTitleEditView.text.toString() != null
           && addContentEditView.text.toString() != null) {
               // db 저장하기 위한 데이터를 contentvalue에 담아줘야하는데
               // 데이터가 많다. contentvalues 객체에 반복 접근
               // 귀찮음 => run or apply

               val contentvalues = ContentValues().apply {
                   put("title", addTitleEditView.text.toString())
                   put("content", addContentEditView.text.toString())
                   put("date", dateParse(addDateView.text.toString()).time)
                   put("completed", 0)
               }
               
               insertTodo(this, contentvalues)

               // 화면을 메인 액티비티로 돌리기
               setResult(Activity.RESULT_OK)
               finish()
           } else {
               showToast(this, "모든 데이터 입력 필요")
           }
        }

        return super.onOptionsItemSelected(item)
    }
}
