package com.dingyl.gankio.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.MyApplication;
import com.dingyl.gankio.activity.IViewBind.IMainActivity;
import com.dingyl.gankio.diskCacheManager.DiskCacheManager;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.entity.GankBeanData;
import com.dingyl.gankio.entity.GankData;
import com.dingyl.gankio.utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observer;

public class FuliPresenter extends BasePresenter {

    private static final String TAG = "FuliPresenter";
    private Context context;
    private DataManager dataManager;
    private IMainActivity iMainActivity;
    DiskCacheManager diskCacheManager = new DiskCacheManager(MyApplication.getContext(),Constants.FULI_IMAGE_CACHE_KEY);

    public FuliPresenter(Context context,IMainActivity iMainActivity){
        this.context = context;
        this.iMainActivity = iMainActivity;
    }

    @Override
    public void onCreate() {
        dataManager = new DataManager(context);
    }


    public void getGankData(int count,int page){
        iMainActivity.showProgressBar();
        Log.d(TAG,"showProgressBar");
        Observable.zip(dataManager.getFuliCategory(count, page),dataManager.getAndroidCategory(count * 5,page),
            new BiFunction<FuliCategory,AndroidCategory,GankData>(){
                @Override
                public GankData apply(FuliCategory fuliCategory,AndroidCategory androidCategory){
                    GankData gankData = new GankData();
                    gankData.setAndroidCategory(androidCategory);
                    gankData.setFuliCategory(fuliCategory);
                    Log.d(TAG,"gankData");
                    return gankData;
                }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GankData, GankBeanData>() {
                    @Override
                    public GankBeanData apply(GankData gankData){
                        GankBeanData gankBeanData = new GankBeanData();
                        gankBeanData.setAndroidBeans(gankData.getAndroidCategory().getResults());
                        gankBeanData.setFuliBeans(gankData.getFuliCategory().getResults());
                        if(gankBeanData != null){
                            makeCache(gankBeanData);
                            Log.d(TAG,"gankBeanData != null");
                        }else{
                            Log.d(TAG,"gankBeanData == null");
                        }
                        return gankBeanData;
                    }
                })
                .subscribe(new Observer<GankBeanData>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(GankBeanData gankBeanData) {
                        if(gankBeanData != null){
                            iMainActivity.onSuccess(gankBeanData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iMainActivity.onError();
                    }

                    @Override
                    public void onComplete() {
                        iMainActivity.hideProgressBar();
                    }
                });
    }

    public void getFuliCategory(int count,int page){
        iMainActivity.showProgressBar();
        dataManager.getFuliCategory(count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<FuliCategory, ArrayList<FuliCategory.FuliBeans>>() {
                    @Override
                    public ArrayList<FuliCategory.FuliBeans> apply(FuliCategory fuliCategory) {
                        ArrayList<FuliCategory.FuliBeans> fuliBeans = fuliCategory.getResults();
                        if(fuliBeans != null){
                            //makeCache(fuliBeans);
                            Log.d(TAG,"fuliBeans != null");
                        }
                        return fuliBeans;
                    }
                })
                .subscribe(new Observer<ArrayList<FuliCategory.FuliBeans>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ArrayList<FuliCategory.FuliBeans> fuliBeans) {
                        if(fuliBeans != null){
                            Log.d(TAG,"onCompleted");
                            Log.d(TAG,fuliBeans.size()+"");
                            //iMainActivity.onSuccess(fuliBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        iMainActivity.onError();
                    }

                    @Override
                    public void onComplete() {
                        iMainActivity.hideProgressBar();
                    }
                });

    }

    public void makeCache(GankBeanData gankBeanData){
        diskCacheManager.put(Constants.FULI_IMAGE_CACHE_KEY,gankBeanData);
    }

    public void loadCache(){
        GankBeanData gankBeanData = diskCacheManager.getSerializable(Constants.FULI_IMAGE_CACHE_KEY);
        if(gankBeanData != null){
            iMainActivity.onSuccess(gankBeanData);
            Log.d(TAG,"loadCache onSuccess");
        }else{
            iMainActivity.onError();
            Log.d(TAG,"loadCache onError");
        }
    }

}
