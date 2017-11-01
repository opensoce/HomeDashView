package com.dx.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by dxd on 2016/11/29 0029.
 */

public class BaseApplication extends Application
{
    protected static Context a=null;

    public static Context getAppContext() {
        if (null==a){
            BaseApplication app = new BaseApplication();
            a =  app.getApplicationContext();
        }
        return a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        a = getApplicationContext();
    }
}