package com.tomcan.frame.vm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.tomcan.frame.m.QuickModel;
import com.tomcan.frame.obs.IBaseLifecycle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author TomCan
 * @description:
 * @date :2020/8/5 15:34
 */
public abstract class QuickViewModel<M extends QuickModel> extends AndroidViewModel implements IBaseLifecycle {
    public String TAG = this.getClass().getSimpleName();
    public final Application application;
    public M model;

    public QuickViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        Log.i(TAG, "onAny");
    }

    @Override
    public void onCreate() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<M> vmClass = (Class<M>) actualTypeArguments[0];
        try {
            model = vmClass.newInstance();
            model.application = application;
        } catch (IllegalAccessException | InstantiationException e) {
            Log.i(TAG, e.toString());
        }
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
}
