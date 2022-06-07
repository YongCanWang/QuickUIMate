package com.tomcan.quickuimate.ui;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tomcan.quickuimate.model.BaseViewModel;


/**
 * @author TomCan
 * @description:
 * @date :2019/3/20 9:17
 */
@SuppressLint("LogNotTimber")
public abstract class NaviBaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    public        String               TAG = this.getClass().getSimpleName();
    public static Context              context;
    public        View                 view;
    public        View                 TagView;
    public        BackHandlerInterface backHandlerInterface;
    public        CallBackLifecycle    callBackLifecycle;
    private       boolean              isFirstVisit;
    public        NaviBaseFragment     lastStckFragment;
    public static AppCompatActivity    activity;
    public        V                    binding;
    public        VM                   vm;

    public NaviBaseFragment() {
    }

    public NaviBaseFragment(NaviBaseFragment lastStckFragment) {
        this.lastStckFragment = lastStckFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (AppCompatActivity) context;
        Log.i(TAG, "onAttach");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBackHandler();
        Log.i(TAG, "onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (view == null) {
            binding = DataBindingUtil.inflate(inflater, initViewID(), container, false);
            view = binding.getRoot();
            isFirstVisit = true;
        } else {
            isFirstVisit = false;
        }
        return this.view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isFirstVisit)
            initData();
        Log.i(TAG, "onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        initView();
        if (callBackLifecycle != null) callBackLifecycle.startUp();
        if (backHandlerInterface != null) backHandlerInterface.stackFragment(this);
        vm = initViewModel();
        if (vm != null) getLifecycle().addObserver(vm);
        if (isFirstVisit) setView();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        BackHandler();
        if (isFirstVisit) {
            addLogic();
        } else {
            resume();
        }
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        loseTrack();
        isFirstVisit = false;
        Log.i(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loseView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
        if (callBackLifecycle != null)
            callBackLifecycle.logOut();
        getLifecycle().removeObserver(vm);
        finish();
        Log.i(TAG, "onDestroy");
    }


    public abstract int initViewID();

    public abstract VM initViewModel();

    public abstract View initView();

    public abstract void initData();

    public abstract void setView();

    public abstract void addLogic();

    public abstract void resume();

    public abstract void loseTrack();

    public abstract void loseView();

    public abstract void finish();

    public abstract boolean back();


    public View getTagView() {
        return TagView;
    }

    public void setTagView(View tagView) {
        TagView = tagView;
    }

    public void setLastStckFragment(NaviBaseFragment lastStckFragment) {
        this.lastStckFragment = lastStckFragment;
    }

    public NaviBaseFragment getLastStckFragment() {
        return lastStckFragment;
    }


    protected void BackHandler() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    back();
                    return true;
                }
                return false;
            }
        });
    }


    protected void goBackHandler() {
        if (activity instanceof BackHandlerInterface) {
            this.backHandlerInterface = (BackHandlerInterface) activity;
        } else {
            throw new ClassCastException("Hosting Activity must implement BackHandlerInterface");
        }
    }


    /**
     * 宿主Activity 必须实现该接口
     */
    public interface BackHandlerInterface {
        void stackFragment(NaviBaseFragment stackFragment);
    }


    public void setCallBackLifecycle(CallBackLifecycle callBackLifecycle) {
        this.callBackLifecycle = callBackLifecycle;
    }

    public interface CallBackLifecycle {
        void startUp();

        void logOut();
    }


    public void replaceStack(NaviBaseFragment fragment, int container) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }


    public void replaceStack(android.app.Fragment fragment, int container) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
    }

    public void pop() {
        activity.getSupportFragmentManager().popBackStack();
    }

    public void remove() {
        activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public void popRemove() {
        pop();
        remove();
    }


}
