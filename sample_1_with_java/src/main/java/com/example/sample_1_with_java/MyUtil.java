package com.example.sample_1_with_java;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static String dataFormat(Date date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }
    public static Date dateParse(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch (Exception e){
            return null;
        }
    }
}
