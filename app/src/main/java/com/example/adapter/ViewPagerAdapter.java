package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;


/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/8/17.
 * TIME : 10:13.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    public ViewPagerAdapter(List<View> views) {
        this.views=views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
