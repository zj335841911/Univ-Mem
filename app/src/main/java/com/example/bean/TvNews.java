package com.example.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.Format;
import java.util.List;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/8/2.
 * TIME : 21:35.
 */

public class TvNews {
    private List<News> BA10TA81wangning;

    public List<News> getBA10TA81wangning() {
        return BA10TA81wangning;
    }

    public void setBA10TA81wangning(List<News> BA10TA81wangning) {
        this.BA10TA81wangning = BA10TA81wangning;
    }
}
