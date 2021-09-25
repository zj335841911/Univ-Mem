package com.example.bean;

import androidx.annotation.NonNull;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/8/16.
 * TIME : 23:19.
 */

public class Time implements Cloneable{
    private String start="";
    private String end="";

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @NonNull
    @Override
    public String toString() {

        return start.isEmpty()?"":start+" - "+end;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
