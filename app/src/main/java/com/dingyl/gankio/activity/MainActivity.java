package com.dingyl.gankio.activity;

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
import com.dingyl.gankio.entity.BaseCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.presenter.FuliPresenter;
import com.dingyl.gankio.utils.NetworkUtils;
import com.dingyl.gankio.view.FuliView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private static final String TAG = "MainActivity-TAG";
    private RecyclerView fuliRecycler;
    private LinearLayout layout;
    private FuliImageAdapter adapter;
    private List<FuliCategory.FuliBeans> fuliBeansList;
    private FuliPresenter fuliPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView(){
        fuliRecycler = findViewById(R.id.main_recycler);
        fuliRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        layout = findViewById(R.id.progress_layout);
    }

    public void initData(){
        fuliBeansList = new ArrayList<>();
        fuliPresenter = new FuliPresenter(this,this);
        fuliPresenter.onCreate();
        if(NetworkUtils.isNetWorkAvailable(this)){
            fuliPresenter.getFuliCategory(10,1);
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
    public void onSuccess(ArrayList<FuliCategory.FuliBeans> fuliBeans) {
        fuliBeansList = fuliBeans;
        adapter = new FuliImageAdapter(fuliBeans,this);
        fuliRecycler.setAdapter(adapter);
    }

    @Override
    public void showProgressBar() {
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        layout.setVisibility(View.GONE);
    }

    @Override
    public void onError() {

    }
}
