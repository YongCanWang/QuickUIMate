package com.tomcan.quickui.ui;

import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tomcan.quickui.v.QuickBaseFragment_V1_0;

/**
 * @author TomCan
 * @description: 加载提示Fragt
 * @date :2021/9/9 11:21
 */
public class HintDalogFragment extends NaviBaseHintDiaLogFragment {


    private LoadingDialog loadingDialog;
    private OnDalogFragtListener onDalogFragtListener;
    public RotateAnimation mAnim;
    private ImageView iv_loading;


    public HintDalogFragment() {
        this(true);
    }

    public HintDalogFragment(boolean isFocusable) {
        this(null, null, isFocusable);
    }

    public HintDalogFragment(QuickBaseFragment_V1_0 naviBaseFragment, OnDalogFragtListener onDalogFragtListener) {
        this(naviBaseFragment, onDalogFragtListener, true);
    }


    public HintDalogFragment(QuickBaseFragment_V1_0 naviBaseFragment, OnDalogFragtListener onDalogFragtListener, boolean isFocusable) {
        super(naviBaseFragment, isFocusable);
        this.onDalogFragtListener = onDalogFragtListener;
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View inflate = View.inflate(getContext(), R.layout.fragment_hint_dalog, null);
////        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NAviHintDiaLogFrag);
//        iv_loading = inflate.findViewById(R.id.iv_loading);
////        iv_loading.setFocusable(false);
////        inflate.setFocusable(false);
//        return inflate;
//    }


    @NonNull
    @Override
    public LoadingDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        loadingDialog = createLoadingDialog();
        return loadingDialog;
    }


    @Override
    public void onStart() {
        super.onStart();
//        initAnim();
//        setDialogWindow();
//        if (null != iv_loading) iv_loading.startAnimation(mAnim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (null != mAnim) mAnim.cancel();
        if (null != onDalogFragtListener) onDalogFragtListener.destroy();
    }


    public LoadingDialog createLoadingDialog() {

        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getContext());
        }
        return loadingDialog;
    }


    public interface OnDalogFragtListener {
        void destroy();
    }


}
