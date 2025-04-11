package com.tomcan.frame.v;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tomcan.frame.vm.QuickViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author TomCan
 * @description: 由于ViewPage中的Fragment在切换之时，会重复的销毁和创建，重走生命周期方法onCreateView、onDestroy
 *               故在onDestroy 方法中不再执行binding的解绑以及ViewModel的释放等代码
 *               以此来保证View的复用，以及避免binding出现NullPointerException错误的问题
 *               并且提供了destroy方法，在需要的时候调用，释放相关资源对象
 * @date :2019/3/20 9:17
 */
@SuppressLint("LogNotTimber")
public abstract class QuickViewPageFragment<V extends ViewDataBinding, VM extends QuickViewModel> extends Fragment {
    public String TAG = this.getClass().getSimpleName();
    private boolean mIsFirstVisit = true;
    public V binding;
    public VM viewModel;
    private ViewModelProvider mViewModelProvider;
    private boolean mIsResume = false;

    @NonNull
    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return super.getDefaultViewModelProviderFactory();
    }

    public QuickViewPageFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType mGenericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        assert mGenericSuperclass != null;
        Type[] actualTypeArguments = mGenericSuperclass.getActualTypeArguments();
        mViewModelProvider = new ViewModelProvider(this);
        if (actualTypeArguments.length > 1) {
            Class<VM> vmClass = (Class<VM>) actualTypeArguments[1];
            viewModel = mViewModelProvider.get(vmClass);
        }
        if (viewModel != null) getLifecycle().addObserver(viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, layout(), container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsFirstVisit) {
            addCallback();
            onStarted();
        }
        mIsFirstVisit = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsResume) {
            onReStart();
        }
        mIsResume = true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void addCallback() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(backPressedEnabled()) {
                    @Override
                    public void handleOnBackPressed() {
                        backPressed();
                    }
                });
    }

    public abstract int layout();

    /**
     * 在整个Activity生命周期中只调用一次
     */
    public abstract void onStarted();

    /**
     * 在整个Activity生命周期中可能会被调用多次，注意Activity首次被创建时不会被调用
     */
    public abstract void onReStart();

    public boolean backPressedEnabled() {
        return false;
    }

    public void backPressed() {

    }

    public <T extends QuickViewModel> T getQuickViewModel(Class<T> quickViewModel) {
        T t = mViewModelProvider.get(quickViewModel);
        getLifecycle().addObserver(t);
        return t;
    }

    /**
     * 解绑 binding
     * 释放 ViewModel
     */
    public void destroy() {
        mIsFirstVisit = true;
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
        if (viewModel != null) {
            getLifecycle().removeObserver(viewModel);
            viewModel = null;
        }
        mViewModelProvider = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
