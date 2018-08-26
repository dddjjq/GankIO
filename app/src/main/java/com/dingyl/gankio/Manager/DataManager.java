package com.dingyl.gankio.Manager;

import android.content.Context;

import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.service.GankIOHelper;
import com.dingyl.gankio.service.GankIOService;

import rx.Observable;


public class DataManager {
    private GankIOService gankIOService;
    public DataManager(Context context){
        gankIOService = GankIOHelper.getInstance(context).getService();
    }

    public Observable<AndroidCategory> getAndroidCategory(int count, int page){
        return gankIOService.getAndroidCategory(count,page);
    }

    public Observable<FuliCategory> getFuliCategory(int count,int page){
        return gankIOService.getFuliCategory(count,page);
    }
}
