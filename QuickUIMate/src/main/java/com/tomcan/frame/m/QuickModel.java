package com.tomcan.frame.m;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.tomcan.frame.obs.IBaseLifecycle;

/**
 * @author Tom灿
 * @description:
 * @date :2024/3/18 10:43
 */
public abstract class QuickModel implements IBaseLifecycle {
    public String TAG = this.getClass().getSimpleName();

    public Application application;

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(TAG, "onAny");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }
}
