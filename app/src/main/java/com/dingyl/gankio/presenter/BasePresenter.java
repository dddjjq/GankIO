package com.dingyl.gankio.presenter;

import com.dingyl.gankio.view.BaseView;

public interface BasePresenter {

    void onCreate();
    void onStop();
    void attachView(BaseView view);

}