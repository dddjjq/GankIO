package com.dingyl.gankio.activity.IViewBind;

import com.dingyl.gankio.entity.FuliCategory;

import java.util.ArrayList;

public interface IMainActivity {

    void onSuccess(ArrayList<FuliCategory.FuliBeans> fuliBeans);

    void showProgressBar();

    void hideProgressBar();

    void onError();
}
