package com.tomcan.quickuimate.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tomcan.frame.v.BasePopupWindow;
import com.tomcan.quickuimate.databinding.DialogMyBinding;

/**
 * @author LiteMob
 * @description:
 * @date: 2025/5/28 15:46
 */
public class MyPop extends BasePopupWindow<DialogMyBinding> {

    public MyPop(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public DialogMyBinding getLayout() {
        return DialogMyBinding.inflate(getLayoutInflater());
    }

    @Override
    public void setView() {
        binding.tvText.setText("Title");
    }
}
