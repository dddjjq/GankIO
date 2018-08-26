package com.dingyl.gankio.view;

import com.dingyl.gankio.entity.BaseCategory;

public interface BaseView {
    void onSuccess(BaseCategory category);
    void onError();

}
