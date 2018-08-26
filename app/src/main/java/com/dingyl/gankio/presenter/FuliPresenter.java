package com.dingyl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.dingyl.gankio.Manager.DataManager;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.view.BaseView;
import com.dingyl.gankio.view.FuliView;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FuliPresenter implements BasePresenter {

    private static final String TAG = "FuliPresenter";
    private Context context;
    private CompositeSubscription compositeSubscription;
    private DataManager dataManager;
    private FuliView fuliView;
    private FuliCategory mFuliCategory;

    public FuliPresenter(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        dataManager = new DataManager(context);
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
        fuliView = (FuliView)view;
    }

    public void getFuliCategory(int count,int page){
        compositeSubscription.add(dataManager.getFuliCategory(count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FuliCategory>() {
                    @Override
                    public void onCompleted() {
                        if(mFuliCategory != null){
                            Log.d(TAG,"onCompleted");
                            Log.d(TAG,mFuliCategory.getResults().size()+"");
                            fuliView.onSuccess(mFuliCategory);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(FuliCategory fuliCategory) {
                        mFuliCategory = fuliCategory;
                    }
                })
        );
    }
}
