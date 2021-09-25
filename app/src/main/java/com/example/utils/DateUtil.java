package com.example.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/6/16.
 * TIME : 11:29.
 */

public class DateUtil {
    public static String Date2String(Date date) {
        String dateString = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        dateString = sdf.format(date);
        return dateString;
    }

}
