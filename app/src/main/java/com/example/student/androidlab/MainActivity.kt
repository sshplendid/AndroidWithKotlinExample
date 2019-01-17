package com.example.student.androidlab

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.* // activity 전체 xml

/*
 일부 ui를 위한 xml(ui xml)도 import해서 이용할 수 있는데
 activity 전체가 아니면 import할때 xml파일명+view.*으로 선언해야함
 */
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_main.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var itemList = mutableListOf<ItemVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivityForResult(intent, 10)
        }
    }

    // 항목의 타입이 두 가지
    // 그 항목을 위한 view 객체 획득하는 holder도 두개 만들어야 한다.
    // view를 find하지 않는다 -> kotlinx
    // activity xml 뿐만 아니라 항목을 구성하기 위한 xml도 extension으로 획득 가능
    class HeaderViewHolder(val view: View): RecyclerView.ViewHolder(view)
    class DataViewHolder(val view: View): RecyclerView.ViewHolder(view)

    // 각 항목을 구성해주는 역할
    // outter의 멤버로 접근하려고 inner로 선언
    inner class MyAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemViewType(position: Int): Int {
            return itemList.get(position).type
        }
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return if(p1 == ItemVO.TYPE_HEADER) {
               val layoutInflater = LayoutInflater.from(p0.context)
                HeaderViewHolder(layoutInflater.inflate(R.layout.item_header,
                        p0, false))
            } else {
                val layoutInflater = LayoutInflater.from(p0.context)
                DataViewHolder(layoutInflater.inflate(R.layout.item_main,
                        p0, false))
            }
        }

        // 전체 몇건인지 조회
        override fun getItemCount(): Int = itemList.size

        // 각 항목의 데이터를 찍기위해서 자동 호출
        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            // 항목 데이터를 획득하고
            val itemVO = itemList.get(p1)
            if(itemVO.type == ItemVO.TYPE_HEADER) {
                val viewHolder = p0 as HeaderViewHolder
                val headerItem = itemVO as HeaderItem
                viewHolder.view.itemHeaderView.text = headerItem.date
            } else {
                val viewHolder = p0 as DataViewHolder
                val dataItem  = itemVO as DataItem

                viewHolder.view.itemTitleView.text = dataItem.title
                viewHolder.view.itemContentView.text = dataItem.content

                if(dataItem.completed) {
                    viewHolder.view.completedIconView.setImageResource(
                            R.drawable.icon_completed
                    )} else {
                    viewHolder.view.completedIconView.setImageResource(R.drawable.icon)
                }
            }
        }
    }

    // 각 항목을 꾸미기 위해서
    inner class MyDecoration(): RecyclerView.ItemDecoration() {
        // 각 항목을 꾸미기 위해 자동 호출
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            // 항목의 index 값 구해서
           val index = parent.getChildAdapterPosition(view)
           // 항목 데이터 획득
            val itemVO = itemList.get(index)
            if(itemVO.type == ItemVO.TYPE_DATA) {
                view.setBackgroundColor(0xFFFFFF.toInt())
                ViewCompat.setElevation(view, 10.0f) // 음영처리?
            }
            // 항목의 여백
            outRect.set(20, 10, 20, 10)
            super.getItemOffsets(outRect, view, parent, state)
        }
    }

    private fun setRecyclerView() {
        // db data select
        val dbList = selectTodos(this)
        itemList = mutableListOf()

        var preDate: String? = null
        for(vo in dbList) {
            if(!vo.date.equals(preDate)) {
                // 새로운 날짜
                val headerItem = HeaderItem(date = dateFormat(Date(vo.date.toLong())))
                itemList.add(headerItem)
                preDate = vo.date
            }
            val completed = vo.completed != 0

            val dataItem = DataItem(id = vo.id, title = vo.title, content = vo.content, completed = completed)
            itemList.add(dataItem)
        }

        // 새로 방향으로 항목 나열
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 항목 구성
        recyclerView.adapter = MyAdapter()

        //항목 꾸미기
        recyclerView.addItemDecoration(MyDecoration())
    }

    // Add 쪽에서 화면이 되돌아올때 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 10 && resultCode == Activity.RESULT_OK) {
            setRecyclerView()
        }
    }
}
