package com.example.univ_mem;

import android.view.View;

import com.example.bean.Note;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/4/22.
 * TIME : 14:27.
 */

public interface RecycylerViewListener {
    void onItemClick(View view, Note note);

    void onItemLongClick(View view, Note note);
}
