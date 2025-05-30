package com.tomcan.quickuimate.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tomcan.frame.v.BaseDialog;
import com.tomcan.quickuimate.databinding.DialogMyBinding;

/**
 * @author LiteMob
 * @description:
 * @date: 2025/5/28 15:40
 */
public class MyDialog extends BaseDialog<DialogMyBinding> {

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public DialogMyBinding getLayout() {
        return DialogMyBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.tvText.setText("Title");
    }
}
