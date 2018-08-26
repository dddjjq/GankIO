package com.dingyl.gankio.presenter;

import android.content.Context;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.view.AndroidView;
import com.dingyl.gankio.view.BaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AndroidPresenter implements BasePresenter{

    private Context context;
    private CompositeSubscription compositeSubscription;
    private DataManager manager;
    private AndroidView androidView;
    private AndroidCategory mAndroidCategory;

    public AndroidPresenter(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(context);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        if(compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(BaseView view) {
        androidView = (AndroidView)view;
    }

    public void getAndroidCategory(int count,int page){
        compositeSubscription.add(manager.getAndroidCategory(count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AndroidCategory>() {
                    @Override
                    public void onCompleted() {
                        if(mAndroidCategory != null){
                            androidView.onSuccess(mAndroidCategory);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AndroidCategory androidCategory) {
                        mAndroidCategory = androidCategory;
                    }
                })
        );
    }
}
