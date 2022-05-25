package com.tomcan.quickuimate.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.tomcan.quickuimate.obs.IBaseViewModelLifecycle;

/**
 * @author TomCan
 * @description:
 * @date :2020/8/5 15:34
 */
public class BaseViewModel extends AndroidViewModel implements IBaseViewModelLifecycle {
    public String TAG = this.getClass().getSimpleName();
    public final Application application;
    private LifecycleOwner owner;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "BaseViewModel");
        this.application = application;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        this.owner = owner;
        Log.i(TAG, "onAny");
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }

    public LifecycleOwner getOwner() {
        return owner;
    }
}
