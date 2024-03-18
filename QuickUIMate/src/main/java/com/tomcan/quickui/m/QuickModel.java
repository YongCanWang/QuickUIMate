package com.tomcan.quickui.m;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.tomcan.quickui.obs.IBaselLifecycle;

/**
 * @author Tom灿
 * @description:
 * @date :2024/3/18 10:43
 */
public class QuickModel implements IBaselLifecycle {
    public String TAG = this.getClass().getSimpleName();

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        Log.i(TAG, "onAny");
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
    }
}
