package com.lxzh123.funcdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LxzhApplication extends Application {
    private static LxzhApplication instance;

    private static List<Activity> activities;

    private final static String TAG="LxzhApplication";

    public static LxzhApplication getInstance(){
        if(instance==null){
            synchronized (LxzhApplication.class){
                instance=new LxzhApplication();
                activities=new ArrayList<Activity>();
            }
        }
        return instance;
    }

    public static List<Activity> getAllActivitys(){
        return activities;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG,"onActivityCreated:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG,"onActivityStarted:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG,"onActivityResumed:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG,"onActivityPaused:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG,"onActivityStopped:"+activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG,"onActivitySaveInstanceState:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG,"onActivityDestroyed:"+activity.getLocalClassName());
            }
        });
    }
}
