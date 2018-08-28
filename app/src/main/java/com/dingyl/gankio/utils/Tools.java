package com.dingyl.gankio.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Tools {

    public static void loadImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    public static void startActivity(Activity activity,Class<?> target){
        Intent intent = new Intent();
        intent.setClass(activity,target);
        activity.startActivity(intent);
    }

}
