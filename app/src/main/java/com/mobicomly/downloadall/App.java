package com.mobicomly.downloadall;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.xunlei.downloadlib.XLTaskHelper;

import org.xutils.x;

import in.mobicomly.download.common.DelegateApplicationPackageManager;

public class App extends Application {
    public static App instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG);
        XLTaskHelper.init(getApplicationContext());
        instance = this;
    }
    public static App appInstance() {
        return instance;
    }
    @Override
    public String getPackageName() {
        if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
            return "com.xunlei.downloadprovider";
        }
        return super.getPackageName();
    }
    @Override
    public PackageManager getPackageManager() {
        if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
            return new DelegateApplicationPackageManager(super.getPackageManager());
        }
        return super.getPackageManager();
    }
}
