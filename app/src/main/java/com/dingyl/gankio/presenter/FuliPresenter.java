package com.dingyl.gankio.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.MyApplication;
import com.dingyl.gankio.activity.IViewBind.IMainActivity;
import com.dingyl.gankio.diskCacheManager.DiskCacheManager;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observer;

public class FuliPresenter extends BasePresenter {

    private static final String TAG = "FuliPresenter";
    private Context context;
    private DataManager dataManager;
    private IMainActivity iMainActivity;
    DiskCacheManager diskCacheManager = new DiskCacheManager(MyApplication.getContext(),Constants.FULI_IMAGE_CACHE);

    public FuliPresenter(Context context,IMainActivity iMainActivity){
        this.context = context;
        this.iMainActivity = iMainActivity;
    }

    @Override
    public void onCreate() {
        dataManager = new DataManager(context);
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
                            makeCache(fuliBeans);
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
                            iMainActivity.onSuccess(fuliBeans);
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

    public void makeCache(ArrayList<FuliCategory.FuliBeans> fuliBeans){
        diskCacheManager.put(Constants.FULI_IMAGE_CACHE_KEY,fuliBeans);
    }

    public void loadCache(){
        ArrayList<FuliCategory.FuliBeans> fuliBeans = diskCacheManager.getSerializable(Constants.FULI_IMAGE_CACHE_KEY);
        if(fuliBeans != null){
            iMainActivity.onSuccess(fuliBeans);
            Log.d(TAG,"loadCache onSuccess");
        }else{
            iMainActivity.onError();
            Log.d(TAG,"loadCache onError");
        }
    }
}
