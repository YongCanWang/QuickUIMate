package com.tomcan.quickuimate.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;

import com.tomcan.quickuimate.R;


/**
 * @author TomCan
 * @description:
 * @date :2019/3/19 16:16
 */
public class LoadingDialog extends AlertDialog {
    private Context context;
    private RotateAnimation mAnim;
    private ImageView ivLoading;
    private boolean windowFocusable = true;
    private boolean isdim = true;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        create();
    }


    @Override
    public void create() {
        super.create();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.height = R.dimen.qb_px_120;
//        layoutParams.width = R.dimen.qb_px_120;
        View inflate = View.inflate(context, R.layout.dialoging_loading, null);
//        ImageView ivLoading = inflate.findViewById(R.id.iv_loading);
//        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams2.height = R.dimen.qb_px_120;
//        layoutParams2.width = R.dimen.qb_px_120;
//        layoutParams2.gravity = Gravity.CENTER;
//        ivLoading.setLayoutParams(layoutParams2);
        setContentView(inflate, layoutParams);
        init();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialoging_loading);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//        init();
//    }


    @Override
    public void show() {
        ivLoading.startAnimation(mAnim);
        super.show();
    }

    @Override
    protected void onStart() {
        setDialogWindow();
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    return false;
                }
                return false;
            }
        });
        super.onStart();
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public void dismiss() {
        mAnim.cancel();
        super.dismiss();
    }


    private void init() {
        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        initAnim();
    }


    private void initAnim() {
        // mAnim = new RotateAnimation(360, 0, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        mAnim = new RotateAnimation(0, 360, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        mAnim.setDuration(2000);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
    }

    public void setDialogWindow() {
        // 点击外部是否消失
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (null != window) {
            // 背景设置为透明
            window.setBackgroundDrawable(getContext().getDrawable(R.drawable.transparent));
//            window.setBackgroundDrawable(getContext().getDrawable(R.color.red));
            // 设置window窗口永远获取不到事件的焦点
            if (!windowFocusable)
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            window.setGravity(Gravity.CENTER);
            // 获取Dialog Window属性对象
            WindowManager.LayoutParams lp = window.getAttributes();
            if (!isdim) // 设置window窗口是否显示阴影
                // 去除阴影
                lp.dimAmount = 0;
//            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            // 设置window 宽度
//            lp.width = R.dimen.qb_px_190;
            // 设置window 高度
//            lp.height = R.dimen.qb_px_190;
            // 居中显示
            lp.gravity = Gravity.CENTER;
            // 设置Dialog Window 属性对象
            window.setAttributes(lp);
//            window.setLayout(R.dimen.qb_px_120, R.dimen.qb_px_120);
        }
    }

    public void isWindowFocusable(boolean windowFocusable) {
        this.windowFocusable = windowFocusable;
    }

    public void isDim(boolean isdim) {
        this.isdim = isdim;
    }


}
