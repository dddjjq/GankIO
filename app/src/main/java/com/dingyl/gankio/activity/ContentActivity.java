package com.dingyl.gankio.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dingyl.gankio.Adapter.ContentListAdapter;
import com.dingyl.gankio.R;
import com.dingyl.gankio.entity.AndroidCategory;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

    private ArrayList<AndroidCategory.AndroidBean> androidBeanArrayList;
    private ListView contentList;
    private ContentListAdapter adapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        contentList = findViewById(R.id.android_content_list);
    }

    private void initData(){
        Intent intent = getIntent();
        if("intent_action_content".equals(intent.getAction())){
            androidBeanArrayList = (ArrayList<AndroidCategory.AndroidBean>)intent.getSerializableExtra("content_list");
        }
        actionBar = getSupportActionBar();
        //actionBar.setIcon(R.drawable.back);
        actionBar.setTitle(androidBeanArrayList.get(0).getPublishedAt().substring(0,10));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContentActivity.this,WebActivity.class);
                intent.putExtra("web_url",androidBeanArrayList.get(position).getUrl());
                intent.putExtra("desc",androidBeanArrayList.get(position).getDesc());
                startActivity(intent);
            }
        });
    }

    private void initListener(){
        adapter = new ContentListAdapter(this,androidBeanArrayList);
        contentList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
