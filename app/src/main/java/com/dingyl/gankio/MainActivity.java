package com.dingyl.gankio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dingyl.gankio.Adapter.FuliImageAdapter;
import com.dingyl.gankio.entity.BaseCategory;
import com.dingyl.gankio.entity.FuliCategory;
import com.dingyl.gankio.presenter.FuliPresenter;
import com.dingyl.gankio.view.FuliView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView fuliRecycler;
    private FuliImageAdapter adapter;
    private List<FuliCategory.FuliBeans> fuliBeansList;
    private FuliPresenter fuliPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView(){
        fuliRecycler = findViewById(R.id.main_recycler);
        fuliRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    public void initData(){
        fuliBeansList = new ArrayList<>();
        fuliPresenter = new FuliPresenter(this);
        fuliPresenter.onCreate();
        fuliPresenter.attachView(fuliView);
        fuliPresenter.getFuliCategory(10,1);
    }

    private FuliView fuliView = new FuliView() {
        @Override
        public void onSuccess(BaseCategory category) {
            fuliBeansList = category.getResults();
            Log.d(TAG,"fuliBeansList size is : " + fuliBeansList.size());
            adapter = new FuliImageAdapter(fuliBeansList,MainActivity.this);
            fuliRecycler.setAdapter(adapter);
            Log.d(TAG,1111+"");
        }

        @Override
        public void onError() {
            Toast.makeText(MainActivity.this,getResources().getString(R.string.request_fail),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        fuliPresenter.onStop();
    }
}
