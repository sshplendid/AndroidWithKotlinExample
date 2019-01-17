package com.example.student.androidlab

import android.app.Activity
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/*
app을 위한 유틸리티 함수
자바에선 static
코틀린에선 top-level로
 */

// toast를 생성하는 함수
// Activity(화면 출력 클래스): 자주 사용하는 기능
// 독립 함수로 만들어도 되지만 마치 Activity에 내장된 함수처럼
// Kotlin Extension을 이용해서 만들어보자
fun Activity.showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun dateFormat(date: Date): String {
    val sd = SimpleDateFormat("yyyy-MM-dd")
    return sd.format(date)
}

fun dateParse(date: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(date)
}