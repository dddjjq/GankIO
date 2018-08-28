package com.dingyl.gankio.service;

import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.AppCategory;
import com.dingyl.gankio.entity.BlindCategory;
import com.dingyl.gankio.entity.ExpandCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.entity.IOSCategory;
import com.dingyl.gankio.entity.RestVideoCategory;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankIOService {

    @GET("Android/count/{count}/page{page}")
    Observable<AndroidCategory> getAndroidCategory(@Path("count")int count, @Path("page")int page);

    @GET("IOS/count/{count}/page{page}")
    Observable<IOSCategory> getIOSCategory(@Path("count")int count, @Path("page")int page);

    @GET("App/count/{count}/page{page}")
    Observable<AppCategory> getAppCategory(@Path("count")int count, @Path("page")int page);

    @GET("瞎推荐/count/{count}/page{page}")
    Observable<BlindCategory> getBlindCategory(@Path("count")int count, @Path("page")int page);

    @GET("拓展视频/count/{count}/page{page}")
    Observable<ExpandCategory> getExpandCategory(@Path("count")int count, @Path("page")int page);

    @GET("福利/{count}/{page}")
    Observable<FuliCategory> getFuliCategory(@Path("count")int count, @Path("page")int page);

    @GET("休息视频/count/{count}/page{page}")
    Observable<RestVideoCategory> getRestCategory(@Path("count")int count, @Path("page")int page);
}
