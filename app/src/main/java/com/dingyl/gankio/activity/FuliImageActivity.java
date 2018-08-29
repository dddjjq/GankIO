package com.dingyl.gankio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dingyl.gankio.R;
import com.dingyl.gankio.utils.Tools;

public class FuliImageActivity extends AppCompatActivity {

    private ImageView fullFuliImage;
    private LinearLayout fullFuliImageLayout;
    private String url;
    private Animation scaleAnimation;
    private Animation translateAnimation;
    private Animation translateAnimationEnd;
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
        fullFuliImageLayout = findViewById(R.id.fuli_full_image_layout);
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(FuliImageActivity.this,R.anim.scale);
        translateAnimation = AnimationUtils.loadAnimation(FuliImageActivity.this,R.anim.fuli_image_translate);
        translateAnimationEnd = AnimationUtils.loadAnimation(FuliImageActivity.this,R.anim.fuli_image_translate_end);
//        fullFuliImage.startAnimation(scaleAnimation);
//        fullFuliImageLayout.startAnimation(translateAnimation);
        fullFuliImageLayout.startAnimation(translateAnimation);
        Intent intent = getIntent();
        url = intent.getStringExtra("fuli_url");
        Tools.loadImage(this,url,fullFuliImage);
    }
    @Override
    public void onStop(){
        super.onStop();
        overridePendingTransition(R.anim.fuli_image_translate_end,R.anim.fuli_image_translate);
    }
}
