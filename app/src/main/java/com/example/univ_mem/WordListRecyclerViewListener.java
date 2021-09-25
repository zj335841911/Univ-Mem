package com.example.univ_mem;

import android.view.View;

import com.example.bean.WordList;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/6/24.
 * TIME : 15:43.
 */

public interface WordListRecyclerViewListener {
    void onItemClick(View view, WordList wordList);

    void onItemLongClick(View view, WordList wordList);
}
