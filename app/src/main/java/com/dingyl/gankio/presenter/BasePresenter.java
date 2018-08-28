package com.dingyl.gankio.presenter;

import com.dingyl.gankio.activity.IViewBind.IMainActivity;
import com.dingyl.gankio.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void removeAllDisposable(){
        if(compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
    void onCreate(){};
    void attachActivity(IMainActivity iMainActivity){};

}