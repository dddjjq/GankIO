package com.dingyl.gankio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dingyl.gankio.R;
import com.dingyl.gankio.utils.Tools;

public class FuliImageActivity extends AppCompatActivity {

    private ImageView fullFuliImage;
    private String url;
    private Animation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fuli_image);
        initView();
        initData();
    }

    private void initView(){
        fullFuliImage = findViewById(R.id.fuli_full_image);
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(FuliImageActivity.this,R.anim.scale);
        fullFuliImage.startAnimation(scaleAnimation);
        Intent intent = getIntent();
        url = intent.getStringExtra("fuli_url");
        Tools.loadImage(this,url,fullFuliImage);
    }

}
