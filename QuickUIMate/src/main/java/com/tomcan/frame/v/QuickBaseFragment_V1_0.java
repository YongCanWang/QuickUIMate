package com.tomcan.frame.v;

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
import androidx.lifecycle.ViewModelProvider;

import com.tomcan.quickui.mate.FragmentMate;
import com.tomcan.frame.vm.QuickViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;


/**
 * @author TomCan
 * @description:
 * @date :2019/3/20 9:17
 */
@Deprecated
@SuppressLint("LogNotTimber")
public abstract class QuickBaseFragment_V1_0<V extends ViewDataBinding, VM extends QuickViewModel> extends Fragment {
    public String TAG = this.getClass().getSimpleName();
    public static Context context;
    public View v;
    public View TagView;
    public final HashMap<String, Object> datas = new HashMap<>();
    public BackHandlerInterface backHandlerInterface;
    public CallBackLifecycle callBackLifecycle;
    private boolean isFirstVisit;
    public QuickBaseFragment_V1_0 lastStckFragment;
    public static AppCompatActivity activity;
    public V binding;
    public VM vm;
    private ParameterizedType genericSuperclass;


    public QuickBaseFragment_V1_0() {
    }

    public QuickBaseFragment_V1_0(CallBackLifecycle callBackLifecycle) {
        this.callBackLifecycle = callBackLifecycle;
    }

    public QuickBaseFragment_V1_0(QuickBaseFragment_V1_0 lastStckFragment) {
        this.lastStckFragment = lastStckFragment;
    }

    public QuickBaseFragment_V1_0(CallBackLifecycle callBackLifecycle, QuickBaseFragment_V1_0 lastStckFragment) {
        this.callBackLifecycle = callBackLifecycle;
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
        genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<VM> vmClass = (Class<VM>) actualTypeArguments[1];
        vm = new ViewModelProvider(this).get(vmClass);
        if (vm != null) getLifecycle().addObserver(vm);
        Log.i(TAG, "onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (v == null) {
//            Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
//            assert genericSuperclass != null;
//            Class<VM> vmClass = (Class<VM>) actualTypeArguments[0];
//            DataBinderMapperImpl dataBinderMapper = new DataBinderMapperImpl();
            binding = DataBindingUtil.inflate(inflater, layout(), container, false);
            binding.setLifecycleOwner(this);
            v = binding.getRoot();
            isFirstVisit = true;
        } else {
            isFirstVisit = false;
        }
        return this.v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isFirstVisit) setView();
        if (isFirstVisit && callBackLifecycle != null) callBackLifecycle.onlyStartup();
        if (callBackLifecycle != null) callBackLifecycle.startup();
        if (backHandlerInterface != null) backHandlerInterface.stackFragment(this);
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        backHandler();
        if (isFirstVisit) {
            function();
        } else {
            resume();
        }
        if (isFirstVisit && callBackLifecycle != null) callBackLifecycle.onlyComplete();
        if (callBackLifecycle != null) callBackLifecycle.complete();
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
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loseView();
        if (callBackLifecycle != null) callBackLifecycle.logout();
        isFirstVisit = false;
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(vm);
        if (callBackLifecycle != null) callBackLifecycle.onlyLogout();
        finish();
        isFirstVisit = true;
        v = null;
        Log.i(TAG, "onDestroy");
    }


    public abstract int layout();

    public abstract void setView();

    public abstract void function();

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

    public void setData(String key, Object obj) {
        datas.put(key, obj);
    }

    public Object getData(String key) {
        return datas.get(key);
    }

    public void setLastStckFragment(QuickBaseFragment_V1_0 lastStckFragment) {
        this.lastStckFragment = lastStckFragment;
    }

    public QuickBaseFragment_V1_0 getLastStckFragment() {
        return lastStckFragment;
    }


    protected void backHandler() {
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
        void stackFragment(QuickBaseFragment_V1_0 stackFragment);
    }


    public void setCallBackLifecycle(CallBackLifecycle callBackLifecycle) {
        this.callBackLifecycle = callBackLifecycle;
    }

    public interface CallBackLifecycle {
        void startup();

        void complete();

        void logout();

        void onlyStartup();

        void onlyComplete();

        void onlyLogout();
    }


    public void replaceStack(QuickBaseFragment_V1_0 fragment, int container) {
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

    public boolean isTop(Fragment fragment) {
        if (null == activity) return false;
        androidx.fragment.app.FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            List<Fragment> fragments = supportFragmentManager.getFragments();
            if (null != fragments && fragments.size() > 0) {
                if (fragment == fragments.get(0)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }


    public void pop() {
//        activity.getSupportFragmentManager().popBackStack();
        FragmentMate.getInstance().pop();
    }

    public void remove() {
//        activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        FragmentMate.getInstance().remove();
    }

    public void popRemove() {
        activity.getSupportFragmentManager().popBackStack();
        FragmentMate.getInstance().remove();
    }


}
