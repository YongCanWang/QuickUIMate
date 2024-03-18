package com.tomcan.quickui.vm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.tomcan.quickui.m.QuickModel;
import com.tomcan.quickui.obs.IBaselLifecycle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author TomCan
 * @description:
 * @date :2020/8/5 15:34
 */
public abstract class QuickViewModel<M extends QuickModel> extends AndroidViewModel implements IBaselLifecycle {
    public String TAG = this.getClass().getSimpleName();
    public final Application application;
    private LifecycleOwner owner;

    public M m;


    public QuickViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "QuickViewModel");
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
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<M> vmClass = (Class<M>) actualTypeArguments[0];
        try {
            m = vmClass.newInstance();
        } catch (IllegalAccessException e) {
            Log.i(TAG, e.toString());
//            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            Log.i(TAG, e.toString());
//            throw new RuntimeException(e);
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

    public LifecycleOwner getOwner() {
        return owner;
    }
}
