package com.tomcan.quickui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * @author TomCan
 * @description: ListView父容器。 有且仅有ListView
 * @date :2020/4/21 10:11
 */
@Deprecated
public class SlideListView extends ViewGroup {

    private       String              TAG              = this.getClass().getSimpleName();
    private       ListView            listView;
    private       float               downY;
    public static int                 scrollMax1;
    public static int                 scrollMax2;
    public static int                 scrollMax3;
    public static int                 scrollMax4;
    public        int                 bottomMin        = 120;
    private       boolean             isDonw;
    private       Scroller            scroller;
    private       int                 moveDistance;
    private       int                 lastMoveDistance = 0;
    private       boolean             istouch;
    private       int                 upInt            = 0;
    private       SlideChangeListener slideChangeListener;
    private       int                 duration         = 800;
    private       boolean             isOpenSlide      = true;
    private       View                view;
    private       int                 childCount;


    public SlideListView(Context context) {
        this(context, null);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("ResourceType")
    private void init() {
        scroller = new Scroller(getContext());
        setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        scrollMax1 = getMeasuredHeight() / 4;
        scrollMax2 = getMeasuredHeight() / 2;
        scrollMax3 = scrollMax1 * 3;
        scrollMax4 = getMeasuredHeight() - bottomMin;
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        view = (View) getChildAt(0);
        childCount = getChildCount();
        if (childCount == 1 && view instanceof ListView) {
            listView = (ListView) view;
            view.layout(0, 0, getWidth(), getHeight());
        } else {
            view.layout(0, 0, getWidth(), view.getMeasuredHeight());
            listView = (ListView) getChildAt(1);
            listView.layout(0, view.getMeasuredHeight(), getWidth(), getHeight());
        }
        measureListView();
    }


    private void measureListView() {
        if (listView != null) {
            if (listView instanceof QuickListView) {
                QuickListView quickListView = (QuickListView) listView;
                View titleHeadView = quickListView.getmTitleHeadView();
                if (titleHeadView != null)
                    bottomMin = quickListView.getmTitleHeadView_easuredHeight();
            } else {
                View childAt = listView.getChildAt(0);
                if (childAt != null)
                    bottomMin = childAt.getMeasuredHeight() + Math.abs(childAt.getPaddingTop());
            }
            if (childCount > 1)
                bottomMin += view.getMeasuredHeight();
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "父View---事件分发: 按下了！！！");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "父View---事件分发: 移动了！！！");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "父View---事件分发: 抬起了！！！");
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        Log.i(TAG, "父View---事件分发: 返回值：" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "父View---事件拦截: 按下了！！！");
                downY = ev.getY();
                if (downY < lastMoveDistance) {
                    istouch = false;
                } else {
                    istouch = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "父View---事件拦截: 移动了！！！");
                float moveY = ev.getY();
                if (isTop(listView) && moveY - downY > 0 && lastMoveDistance != getMeasuredHeight() && moveY >= lastMoveDistance) {
                    Log.i(TAG, "父View---事件拦截: 返回值：" + "true1");
                    return true;
                } else if (moveY - downY < 0 && lastMoveDistance != 0 && moveY >= lastMoveDistance) {
                    Log.i(TAG, "父View---事件拦截: 返回值：" + "true2");
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "父View---事件拦截: 抬起了！！！");
                break;
        }
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "父View---事件拦截: 返回值：" + b);
        return b;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "父View---事件处理: 移动了！！！");
                if (isDonw)
                    downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "父View---事件处理: 移动了！！！");
                if (!isSlide())
                    return false;
                float moveY = event.getY();
                float move_V = moveY - downY;
                if (move_V <= 0)
                    moveDistance = 0;
                moveDistance = (int) move_V + lastMoveDistance;
                scrollTo(0, -moveDistance);
                isShowTitleView(moveY);
                if (slideChangeListener != null)
                    slideChangeListener.slideChange(moveY);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "父View---事件处理: 移动了！！！");
                if (!isSlide())
                    return false;
                float upY = event.getY();
                if (moveDistance <= scrollMax1) {
                    upInt = 0;
                } else if (moveDistance > scrollMax1 && moveDistance <= scrollMax2) {
                    upInt = scrollMax2;
                } else if (moveDistance > scrollMax2 && moveDistance <= scrollMax3) {
                    upInt = scrollMax2;
                } else if (moveDistance > scrollMax3 && moveDistance <= getMeasuredHeight()) {
                    upInt = scrollMax4;
                }

                computeScrollListView(moveDistance, upInt, duration);
                if (slideChangeListener != null) {
                    slideChangeListener.slideChange(upY);
                    slideChangeListener.slideSwitchChange(upInt);
                }
                break;
        }
        return false;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {// 返回值 true 没有移动到终点
            // 获取计算的一个位置
            int currY = scroller.getCurrY();
            // 移动到获取的位置
            scrollTo(0, currY);
            postInvalidate();// 触发computeScroll
        }
    }

    public void scrollToTop() {
        computeScrollListView(upInt, 0, duration);
        if (slideChangeListener != null)
            slideChangeListener.slideSwitchChange(upInt);
    }

    public void scrollToBottom() {
        if (listView != null) {
            View childAt = listView.getChildAt(0);
            if (childAt != null)
                scrollMax4 = childAt.getMeasuredHeight();
        }
        computeScrollListView(upInt, 0, duration);
        if (slideChangeListener != null)
            slideChangeListener.slideSwitchChange(upInt);
    }


    public void scrollTo2Bottom() {
        computeScrollListView(upInt, scrollMax2, duration);
        if (slideChangeListener != null)
            slideChangeListener.slideSwitchChange(upInt);
    }


    public void computeScrollListView(int start, int end, int duration) {
        upInt = end;
        scroller.startScroll(0, -start, 0, -(end - start), duration);
        computeScroll();
        lastMoveDistance = end;
        downY = end;
        isDonw = false;
    }


    public void openSwitchSlide(boolean openSlide) {
        isOpenSlide = openSlide;
    }

    public void setSlideDuration(int duration) {
        this.duration = duration;
    }

    public void isShowTitleView(float moveY) {
        if (moveY >= 0 && moveY < SlideListView.scrollMax1 && listView.getCount() > 0) {
            view.setPadding(0, view.getPaddingTop(), 0, view.getPaddingBottom());
        } else if (moveY == SlideListView.scrollMax1 && listView.getCount() > 0) {
            view.setPadding(0, -view.getPaddingTop(), 0, -view.getPaddingBottom());
        }
    }


    public boolean isSlide() {
        if (!istouch || !isOpenSlide || listView.getChildCount() <= listView.getHeaderViewsCount() + listView.getFooterViewsCount()) {
            return false;
        } else {
            return true;
        }
    }


    public boolean isTop(final ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }

    public boolean isBottom(final ListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }


    public void setSlideChangeListener(SlideChangeListener slideChangeListener) {
        this.slideChangeListener = slideChangeListener;
    }

    public interface SlideChangeListener {
        void slideChange(float change);

        void slideSwitchChange(int change);
    }


}

