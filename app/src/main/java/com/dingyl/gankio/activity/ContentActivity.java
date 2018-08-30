package com.dingyl.gankio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dingyl.gankio.Adapter.ContentListAdapter;
import com.dingyl.gankio.R;
import com.dingyl.gankio.entity.AndroidCategory;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

    private ArrayList<AndroidCategory.AndroidBean> androidBeanArrayList;
    private ListView contentList;
    private ContentListAdapter adapter;

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
    }

    private void initListener(){
        adapter = new ContentListAdapter(this,androidBeanArrayList);
        contentList.setAdapter(adapter);
    }

}
