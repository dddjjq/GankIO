package com.dingyl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.MyApplication;
import com.dingyl.gankio.activity.IViewBind.IMainActivity;
import com.dingyl.gankio.diskCacheManager.DiskCacheManager;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.utils.Constants;
import com.dingyl.gankio.view.AndroidView;
import com.dingyl.gankio.view.BaseView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class AndroidPresenter extends BasePresenter{

    private static final String TAG = "AndroidPresenter";
    private Context context;
    private DataManager manager;
    private IMainActivity iMainActivity;
    private ArrayList<AndroidCategory.AndroidBean> androidBeans;
    DiskCacheManager diskCacheManager = new DiskCacheManager(MyApplication.getContext(),Constants.ANDROID_CATEGOTY_CACHE);

    public AndroidPresenter(Context context,IMainActivity iMainActivity){
        this.context = context;
        this.iMainActivity = iMainActivity;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(context);
    }


    public void getAndroidCategory(int count,int page){
        manager.getAndroidCategory(count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<AndroidCategory, ArrayList<AndroidCategory.AndroidBean>>() {
                    @Override
                    public ArrayList<AndroidCategory.AndroidBean> apply(AndroidCategory category){
                        ArrayList<AndroidCategory.AndroidBean> androidBeans = category.getResults();
                        if(androidBeans != null){
                            makeCache(androidBeans);
                        }
                    }
                })
                .subscribe(new Observer<AndroidCategory>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(AndroidCategory androidCategory) {
                        if(androidBeans != null){
                            iMainActivity.onSuccess(androidBeans);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }




                });
    }

    public void makeCache(ArrayList<AndroidCategory.AndroidBean> androidBeans){
        diskCacheManager.put(Constants.ANDROID_CATEGOTY_CACHE_KEY,androidBeans);
    }

    public void loadCache(){
        ArrayList<AndroidCategory.AndroidBean> androidBeans = diskCacheManager.getSerializable(Constants.ANDROID_CATEGOTY_CACHE_KEY);
        if(androidBeans != null){
            iMainActivity.onSuccess(androidBeans);
            Log.d(TAG,"loadCache onSuccess");
        }else{
            iMainActivity.onError();
            Log.d(TAG,"loadCache onError");
        }
    }
}
