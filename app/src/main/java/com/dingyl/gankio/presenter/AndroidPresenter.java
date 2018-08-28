package com.dingyl.gankio.presenter;

import android.content.Context;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.view.AndroidView;
import com.dingyl.gankio.view.BaseView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AndroidPresenter extends BasePresenter{

    private Context context;
    private DataManager manager;
    private AndroidView androidView;
    private AndroidCategory mAndroidCategory;

    public AndroidPresenter(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(context);
    }


    public void getAndroidCategory(int count,int page){
        manager.getAndroidCategory(count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AndroidCategory>() {
                    @Override
                    public void onNext(AndroidCategory androidCategory) {
                        if(mAndroidCategory != null){
                            androidView.onSuccess(mAndroidCategory);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }


                });
    }
}
