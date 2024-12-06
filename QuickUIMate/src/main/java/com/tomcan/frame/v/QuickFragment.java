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
 * @description:
 * @date :2019/3/20 9:17
 */
@SuppressLint("LogNotTimber")
public abstract class QuickFragment<V extends ViewDataBinding, VM extends QuickViewModel> extends Fragment {
    public String TAG = this.getClass().getSimpleName();
    private boolean mIsFirstVisit = true;
    public V binding;
    public VM viewModel;
    private ParameterizedType mGenericSuperclass;
    private ViewModelProvider mViewModelProvider;

    public QuickFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGenericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        assert mGenericSuperclass != null;
        Type[] actualTypeArguments = mGenericSuperclass.getActualTypeArguments();
        Class<VM> vmClass = (Class<VM>) actualTypeArguments[1];
        mViewModelProvider = new ViewModelProvider(this);
        viewModel = mViewModelProvider.get(vmClass);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisit) {
            onReStart();
        }
        mIsFirstVisit = false;
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

    @Override
    public void onDestroy() {
        mIsFirstVisit = true;
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
        getLifecycle().removeObserver(viewModel);
        mViewModelProvider = null;
        viewModel = null;
        super.onDestroy();
    }
}
