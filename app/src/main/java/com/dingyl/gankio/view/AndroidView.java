package com.dingyl.gankio.view;

import com.dingyl.gankio.entity.BaseCategory;

public interface AndroidView extends BaseView{

    public void onSuccess(BaseCategory category);
    public void onError();

}
