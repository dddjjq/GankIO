package com.dingyl.gankio.activity.IViewBind;

import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.entity.GankBeanData;

import java.util.ArrayList;

public interface IMainActivity {

    void onSuccess(GankBeanData gankBeanData);

    void showProgressBar();

    void hideProgressBar();

    void onError();
}
