package com.tomcan.quickui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tomcan.quickui.R;


/**
 * @author TomCan
 * @description:
 * @date :2019/7/16 11:59
 */
public class QuickListView extends ListView implements AbsListView.OnScrollListener {

    private       String                 TAG      = "SlideListView";
    private final Context                context;
    public        View                   mLoadingFootView;
    private       int                    totalItemCount;
    private       OnScrollBottomListener onScrollBottomListener;
    private       int                    lastVisiblePosition;
    private       boolean                isLoaded = false;
    private       int                    mLoadingFootView_easuredHeight;

    private boolean                      isShowLoadingView = true;
    private int                          mTitleHeadView_easuredHeight;
    private View                         mTitleHeadView;
    private OnTitleHeadViewClickListener onTitleHeadViewClickListener;
    private int                          mTitleHeadViewPaddingTop;
    private int                          mTitleHeadViewPaddingBottom;


    private TextView     tv_keyword;
    private TextView     tv_sum;
    private LinearLayout ll_title;
    private View         mOperationHeadView;
    private int          mOperationHeadViewEasuredHeight;
    private int          mOperationHeadViewPaddingTop;
    private int          mOperationHeadViewPaddingBottom;

    public QuickListView(Context context) {
        this(context, null);
    }


    public QuickListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public QuickListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initTitleHeadView();
//        initOperationHeadView();
        initFootView();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }



    private void findOperationHeadViewID() {
    }


    private void initTitleHeadView() {
        mTitleHeadView = View.inflate(context, R.layout.quikelistview_title_head_view, null);
        mTitleHeadView.measure(0, 0);
        mTitleHeadView_easuredHeight = mTitleHeadView.getMeasuredHeight();
        mTitleHeadViewPaddingTop = mTitleHeadView.getPaddingTop();
        mTitleHeadViewPaddingBottom = mTitleHeadView.getPaddingBottom();
        findViewID();
        resetmTitleHeadView();
        addHeaderView(mTitleHeadView);
        mTitleHeadView.setOnClickListener(mTitleHeadViewClickListener);
    }

    private void findViewID() {
        ll_title = mTitleHeadView.findViewById(R.id.ll_title);
        tv_keyword = mTitleHeadView.findViewById(R.id.tv_keyword);
        tv_sum = mTitleHeadView.findViewById(R.id.tv_sum);
    }


    private void initFootView() {
        mLoadingFootView = LayoutInflater.from(context).inflate(R.layout.quikelistview_foot_view, null);
        mLoadingFootView.setOnClickListener(null);
        mLoadingFootView.measure(0, 0);
        mLoadingFootView_easuredHeight = mLoadingFootView.getMeasuredHeight();
        resetmLoadingFootView();
        addFooterView(mLoadingFootView);
        setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isLoaded && isShowLoadingView && lastVisiblePosition == totalItemCount && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            isLoaded = true;
            mLoadingFootView.setPadding(0, 0, 0, 0);
            setSelection(getCount());
            if (onScrollBottomListener != null) onScrollBottomListener.scrollBottom();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisiblePosition = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    private OnClickListener mTitleHeadViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onTitleHeadViewClickListener != null)
                onTitleHeadViewClickListener.clickTitleHead(view);
        }
    };

    public void resetmTitleHeadView() {
        mTitleHeadView.setPadding(0, -mTitleHeadViewPaddingTop, 0, -mTitleHeadViewPaddingBottom);
    }

    public void showmTitleHeadView() {
        mTitleHeadView.setPadding(0, mTitleHeadViewPaddingTop, 0, mTitleHeadViewPaddingBottom);
    }

    public void resetmOperationHeadView() {
        mOperationHeadView.setPadding(0, -mOperationHeadViewPaddingTop, 0, -mOperationHeadViewPaddingBottom);
    }

    public void showmOperationHeadView() {
        mOperationHeadView.setPadding(0, mOperationHeadViewPaddingTop, 0, mOperationHeadViewPaddingBottom);
    }


    public void resetmLoadingFootView() {
        this.isLoaded = false;
        mLoadingFootView.setPadding(0, -mLoadingFootView_easuredHeight, 0, 0);
        setSelection(getCount());
    }


    public void setScrollBottomListener(OnScrollBottomListener onScrollBottomListener) {
        this.onScrollBottomListener = onScrollBottomListener;
    }


    public int getmTitleHeadView_easuredHeight() {
        return mTitleHeadView_easuredHeight;
    }


    public View getmTitleHeadView() {
        return mTitleHeadView;
    }

    public void setKeyWord(String keyWord) {
        tv_keyword.setText(keyWord);
    }

    public void setKeyWordColor(int color) {
        tv_keyword.setTextColor(color);
    }

    public void setKeyWordSize(float size) {
        tv_keyword.setTextSize(size);
    }

    public void setKeySum(String keySum) {
        tv_sum.setText(keySum);
    }

    public void setKeySumColor(int color) {
        tv_sum.setTextColor(color);
    }

    public void setKeySumSize(float size) {
        tv_sum.setTextSize(size);
    }

    public void setTitleBackgroundColor(int color) {
        ll_title.setBackgroundColor(color);
    }

    public interface OnScrollBottomListener {
        void scrollBottom();
    }

    public void setOnTitleHeadViewClickListener(OnTitleHeadViewClickListener onTitleHeadViewClickListener) {
        this.onTitleHeadViewClickListener = onTitleHeadViewClickListener;
    }


    public interface OnTitleHeadViewClickListener {
        void clickTitleHead(View view);
    }

    public boolean getIsShowLoadingView() {
        return isShowLoadingView;
    }

    public void setIsShowLoadingView(boolean showLoadingView) {
        isShowLoadingView = showLoadingView;
    }


}
