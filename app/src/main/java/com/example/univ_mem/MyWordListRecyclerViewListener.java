package com.example.univ_mem;

import android.view.View;

import com.example.bean.MyWordList;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/4/22.
 * TIME : 13:56.
 */

public interface MyWordListRecyclerViewListener {
    void onItemClick(View view, MyWordList myWordList);

    void onItemLongClick(View view, MyWordList myWordList);
}
