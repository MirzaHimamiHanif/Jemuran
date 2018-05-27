package org.pindad.jemuran.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class MyApplication extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
