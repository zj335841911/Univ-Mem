package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.univ_mem.R;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/7/23.
 * TIME : 22:29.
 */

public class WordItemFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_word_items, container, false);
        return view;
    }
}
