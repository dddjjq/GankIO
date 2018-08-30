package com.dingyl.gankio.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class GankData {

    private FuliCategory fuliCategory;
    private AndroidCategory androidCategory;

    public GankData(){
    }

    public FuliCategory getFuliCategory() {
        return fuliCategory;
    }

    public void setFuliCategory(FuliCategory fuliCategory) {
        this.fuliCategory = fuliCategory;
    }

    public AndroidCategory getAndroidCategory() {
        return androidCategory;
    }

    public void setAndroidCategory(AndroidCategory androidCategory) {
        this.androidCategory = androidCategory;
    }

}
