package com.dingyl.gankio;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class MyApplication extends Application{

    private static MyApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    /**
    * 获取全局context
    */
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    /**
     *
     * @return app版本号
     */
    public static int getAppVersion(){
        Context context = getContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
