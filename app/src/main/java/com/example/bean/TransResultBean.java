package com.example.bean;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/8/30.
 * TIME : 19:26.
 */


public class TransResultBean {
    private String src;//要翻译的文本
    private String dst;//翻译后的文本

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
