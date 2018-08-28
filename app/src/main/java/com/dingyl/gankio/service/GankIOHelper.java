package com.dingyl.gankio.service;

import android.content.Context;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankIOHelper {
    private Context context;
    private OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static GankIOHelper instance = null;
    private Retrofit retrofit = null;

    public static GankIOHelper getInstance(Context context) {
        if(instance == null){
            instance = new GankIOHelper(context);
        }
        return instance;
    }

    private GankIOHelper(Context context){
        this.context = context;
        init();
    }

    private void init(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.io/api/data/")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public GankIOService getService(){
        return retrofit.create(GankIOService.class);
    }
}
