package com.tomcan.quickui.vi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author TomCan
 * @description:
 * @date:2022/8/27 18:33
 */
public class QuickRecyclerView extends RecyclerView {
    public QuickRecyclerView(@NonNull Context context) {
        super(context);
    }

    public QuickRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuickRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (screenState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

        }
    }
}
