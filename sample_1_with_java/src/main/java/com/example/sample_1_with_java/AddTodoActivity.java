package com.example.sample_1_with_java;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    EditText addTitleEditView;
    EditText addContentEditView;
    TextView addDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        addTitleEditView = findViewById(R.id.addTitleEditView);
        addContentEditView = findViewById(R.id.addContentEditView);
        addDateView = findViewById(R.id.addDateView);

        addDateView.setText(MyUtil.dataFormat(new Date()));

        addDateView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        addDateView.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add){
            if(addTitleEditView.getText().toString() != null && addContentEditView.getText().toString() != null){
                ContentValues values = new ContentValues();
                values.put("title", addTitleEditView.getText().toString());
                values.put("content", addContentEditView.getText().toString());
                values.put("date", addDateView.getText().toString());
                values.put("completed", 0);
                ExecuteSQLUtil.insertTodo(this, values);

                setResult(Activity.RESULT_OK);

                finish();
            }else {
                MyUtil.showToast(this, "모든 데이터가 입력되지 않았습니다.");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
