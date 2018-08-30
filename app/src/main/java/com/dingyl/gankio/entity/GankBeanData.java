package com.dingyl.gankio.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class GankBeanData implements Serializable{

    private ArrayList<FuliCategory.FuliBeans> fuliBeans;
    private ArrayList<AndroidCategory.AndroidBean> androidBeans;

    public ArrayList<FuliCategory.FuliBeans> getFuliBeans() {
        return fuliBeans;
    }

    public void setFuliBeans(ArrayList<FuliCategory.FuliBeans> fuliBeans) {
        this.fuliBeans = fuliBeans;
    }

    public ArrayList<AndroidCategory.AndroidBean> getAndroidBeans() {
        return androidBeans;
    }

    public void setAndroidBeans(ArrayList<AndroidCategory.AndroidBean> androidBeans) {
        this.androidBeans = androidBeans;
    }
}
