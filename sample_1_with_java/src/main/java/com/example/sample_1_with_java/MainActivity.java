package com.example.sample_1_with_java;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Rect;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;

    ArrayList<ItemVO> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        setRecyclerView();

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setRecyclerView(){
        ArrayList<TodoVO> dbList = ExecuteSQLUtil.selectTodos(this);
        itemList = new ArrayList<>();

        Calendar preDate = null;
        for(TodoVO vo : dbList){
            GregorianCalendar currentDate = new GregorianCalendar();
            currentDate.setTime(MyUtil.dateParse(vo.date));

            if(!currentDate.equals(preDate)){
                HeaderItem headerItem = new HeaderItem(vo.date);
                itemList.add(headerItem);
                preDate = currentDate;
            }

            boolean completed = vo.completed != 0;
            DataItem dataItem = new DataItem(vo.id, vo.title, vo.content, completed);
            itemList.add(dataItem);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addItemDecoration(new MyDecoration());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            setRecyclerView();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerView;
        public HeaderViewHolder(View view){
            super(view);
            headerView = view.findViewById(R.id.itemHeaderView);
        }
    }
    class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView completedIconView;
        TextView itemTitleView;
        TextView itemContentView;
        public DataViewHolder(View view){
            super(view);
            completedIconView = view.findViewById(R.id.completedIconView);
            itemTitleView = view.findViewById(R.id.itemTitleView);
            itemContentView = view.findViewById(R.id.itemContentView);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public int getItemViewType(int position) {
            return itemList.get(position).getType();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == ItemVO.TYPE_HEADER){
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false));
            }else {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new DataViewHolder(inflater.inflate(R.layout.item_main, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemVO itemVO = itemList.get(position);
            if(itemVO.getType() == ItemVO.TYPE_HEADER){
                HeaderViewHolder viewHolder = (HeaderViewHolder)holder;
                HeaderItem headerItem = (HeaderItem)itemVO;
                viewHolder.headerView.setText(headerItem.date);
            }else {
                final DataViewHolder viewHolder = (DataViewHolder)holder;
                final DataItem dataItem = (DataItem)itemVO;
                viewHolder.itemTitleView.setText(dataItem.title);
                viewHolder.itemContentView.setText(dataItem.content);
                if(dataItem.completed){
                    viewHolder.completedIconView.setImageResource(R.drawable.icon_completed);
                }else {
                    viewHolder.completedIconView.setImageResource(R.drawable.icon);
                }

                viewHolder.completedIconView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d("kkang","completed:"+dataItem.completed);
                        if(dataItem.completed){
                            ContentValues values = new ContentValues();
                            values.put("completed", 0);
                            ExecuteSQLUtil.updateTodo(MainActivity.this, values, "_id="+dataItem.id);
                            viewHolder.completedIconView.setImageResource(R.drawable.icon);
                            dataItem.completed = false;
                        }else {
                            ContentValues values = new ContentValues();
                            values.put("completed", 1);
                            ExecuteSQLUtil.updateTodo(MainActivity.this, values, "_id="+dataItem.id);
                            viewHolder.completedIconView.setImageResource(R.drawable.icon_completed);
                            dataItem.completed = true;
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }
    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int index = parent.getChildAdapterPosition(view);
            ItemVO itemVO = itemList.get(index);
            if(itemVO.getType() == ItemVO.TYPE_DATA){
                view.setBackgroundColor(0xFFFFFFFF);
                ViewCompat.setElevation(view, 10.0f);
            }
            outRect.set(20, 10, 20, 10);
        }
    }
}
