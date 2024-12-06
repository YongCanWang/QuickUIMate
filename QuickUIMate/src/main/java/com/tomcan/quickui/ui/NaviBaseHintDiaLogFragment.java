package com.tomcan.quickui.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.tomcan.frame.v.QuickBaseFragment_V1_0;

/**
 * @author TomCan
 * @description:
 * @date :2021/9/17 10:38
 */
public class NaviBaseHintDiaLogFragment extends DialogFragment implements DialogInterface.OnKeyListener {
    private final  String            TAG         = NaviBaseHintDiaLogFragment.this.getClass().getSimpleName();
    private        Context           context;
    private        AppCompatActivity activity;
    private QuickBaseFragment_V1_0 naviBaseFragment;
    private static boolean           isKey;
    public         boolean           isFocusable = true;

    public NaviBaseHintDiaLogFragment() {
        this(null, true);
    }

    public NaviBaseHintDiaLogFragment(QuickBaseFragment_V1_0 naviBaseFragment, boolean isFocusable) {
        this.naviBaseFragment = naviBaseFragment;
        this.isFocusable = isFocusable;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (null != dialog) {
            dialog.setOnKeyListener(this);
        }
    }


    @Override
    public void onDestroy() {
        if (isKey) if (null != naviBaseFragment) naviBaseFragment.back();
        isKey = false;
        super.onDestroy();
    }

    public void remove() {
        if (null != activity)
        activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    public void pop() {
        if (null != activity) activity.getSupportFragmentManager().popBackStack();
    }

    public void popRemove() {
        pop();
        remove();
    }


    public void setAttachFragt(QuickBaseFragment_V1_0 naviBaseFragment) {
        this.naviBaseFragment = naviBaseFragment;
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            isKey = true;
            return false;
        }
        return false;
    }
}
