package com.example.student.androidlab

/*
ItemVO
화면의 투두 항목 하나하나를 표현하기 위한 VO
- 날짜
- todo
두 데이터를 동일 타입으로 처리하기 위해 상위 만들고 하위 상속으로
 */

abstract class ItemVO {
    abstract val type: Int
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_DATA = 1
    }
}

data class HeaderItem(override val type: Int = ItemVO.TYPE_HEADER, var date: String): ItemVO()
data class DataItem(override val type: Int = ItemVO.TYPE_DATA,
                    var id: Int,
                    var title: String,
                    var content: String,
                    var completed: Boolean = false): ItemVO()
