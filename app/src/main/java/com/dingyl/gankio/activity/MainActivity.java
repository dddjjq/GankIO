package com.dingyl.gankio.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dingyl.gankio.Adapter.FuliImageAdapter;
import com.dingyl.gankio.R;
import com.dingyl.gankio.activity.IViewBind.IMainActivity;
import com.dingyl.gankio.entity.AndroidCategory;
import com.dingyl.gankio.entity.BaseCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.entity.GankBeanData;
import com.dingyl.gankio.entity.GankData;
import com.dingyl.gankio.presenter.FuliPresenter;
import com.dingyl.gankio.utils.NetworkUtils;
import com.dingyl.gankio.view.FuliView;
import com.dingyl.gankio.view.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private static final String TAG = "MainActivity-TAG";
    private RecyclerView fuliRecycler;
    private MySwipeRefreshLayout swipeRefreshLayout;
    private FuliImageAdapter adapter;
    private List<FuliCategory.FuliBeans> fuliBeansList;
    private List<AndroidCategory.AndroidBean> androidBeanList;
    private FuliPresenter fuliPresenter;
    private int mPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setOnScroll();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setContentRecycler(fuliRecycler);
        swipeRefreshLayout.setLoadDataListener(new MySwipeRefreshLayout.LoadDataListener() {
            @Override
            public void loadData() {
                swipeRefreshLayout.setRefreshing(true);
                mPage ++ ;
                fuliPresenter.getGankData(10,mPage);
            }
        });
    }
    private void initView(){
        fuliRecycler = findViewById(R.id.main_recycler);
        fuliRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        swipeRefreshLayout = findViewById(R.id.swipe_layout);

    }

    public void initData(){
        fuliBeansList = new ArrayList<>();
        androidBeanList = new ArrayList<>();
        fuliPresenter = new FuliPresenter(this,this);
        fuliPresenter.onCreate();
        if(NetworkUtils.isNetWorkAvailable(this)){
            fuliPresenter.getGankData(10,1);
            Log.d(TAG,"isNetWorkAvailable");
        }else{
            fuliPresenter.loadCache();
            Log.d(TAG,"isNetWorkNotAvailable");
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        fuliPresenter.removeAllDisposable();
    }

    @Override
    public void onSuccess(GankBeanData gankBeanData) {
        if(!fuliBeansList.containsAll(gankBeanData.getFuliBeans())){
            fuliBeansList.addAll(gankBeanData.getFuliBeans());
        }
        if(!androidBeanList.containsAll(gankBeanData.getAndroidBeans())){
            androidBeanList.addAll(gankBeanData.getAndroidBeans());
        }
        adapter = new FuliImageAdapter(gankBeanData,this);
        fuliRecycler.setAdapter(adapter);
    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError() {

    }

    private void setOnScroll(){
        fuliRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            fuliPresenter.getGankData(10,1);
        }
    };

}
