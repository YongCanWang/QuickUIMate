package com.tomcan.quickuimate.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.databinding.ViewDataBinding;

import com.tomcan.quickuimate.model.BaseViewModel;

import java.util.ArrayList;


/**
 * @author TomCan
 * @description:
 * @date :2021/8/23 14:03
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends NaviBaseFragment<V, VM>
        implements HintDalogFragment.OnDalogFragtListener {

    private OnBaseFragListener onBaseFragListener;

    private HintDalogFragment hintDalogFragment;

    public BaseFragment() {
    }

    public BaseFragment(CallBackLifecycle callBackLifecycle) {
        super(callBackLifecycle);
    }

    public BaseFragment(NaviBaseFragment lastStckFragment) {
        super(lastStckFragment);
    }

    public BaseFragment(CallBackLifecycle callBackLifecycle, NaviBaseFragment lastStckFragment) {
        super(callBackLifecycle, lastStckFragment);
    }

    @Override
    public void onDestroyView() {
        dismissLoadingDalogFragt();
        super.onDestroyView();
    }


    /**
     * 显示Loading DialogFragment
     */
    public void showLoadingDialogFragt() {
        showLoadingDialogFragt(true);
    }


    /**
     * 显示Loading DialogFragment
     *
     * @param isFocusable 是否获取事件焦点
     */
    public void showLoadingDialogFragt(boolean isFocusable) {
        createLoadingDialogFragt(isFocusable);
        if (!hintDalogFragment.isAdded() && !hintDalogFragment.isVisible()) {
            hintDalogFragment.show(getFragmentManager(), "hintLoadingDalog");
        }
    }


    /**
     * 移除Loading FialohFragment
     */
    public void dismissLoadingDalogFragt() {
        if (null != hintDalogFragment) {
            hintDalogFragment.dismiss();
        }
    }

    /**
     * 创建Loading DialogFragment
     */
    private void createLoadingDialogFragt() {
        createLoadingDialogFragt(true);
    }

    /**
     * 创建Loading DialogFragment
     *
     * @param isFocusable View 是否获取事件焦点
     */
    private void createLoadingDialogFragt(boolean isFocusable) {
        if (null == hintDalogFragment) {
            hintDalogFragment = new HintDalogFragment(this, this, isFocusable);
        }
    }


    /**
     * Loading DoaLogFragment 销毁回调
     */
    @Override
    public void destroy() {
        if (null != onBaseFragListener) onBaseFragListener.onDialogFragDismiss();
    }


    public interface OnBaseFragListener {
        void onDialogFragDismiss();
    }

    public void setOnBaseFragListener(OnBaseFragListener onBaseFragListener) {
        this.onBaseFragListener = onBaseFragListener;
    }


    public void setChildViewClickEnabled(ViewGroup viewGroup, boolean isClick, ArrayList<Integer> filtrationViews) {
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (null != childAt) {
                    if (null != filtrationViews && filtrationViews.contains(childAt.getId()))
                        continue;
                    if (childAt instanceof ViewGroup) {
                        childAt.setClickable(isClick);
                        setChildViewClickEnabled((ViewGroup) childAt, isClick, filtrationViews);
                    } else {
                        childAt.setClickable(isClick);
                    }
                }
            }
        }
    }

}
