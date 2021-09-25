package com.example.bean;

import java.util.List;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/8/30.
 * TIME : 17:58.
 */


public class TranslateResult {
    private String from;
    private String to;
    private List<TransResultBean> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TransResultBean> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TransResultBean> trans_result) {
        this.trans_result = trans_result;
    }
}
