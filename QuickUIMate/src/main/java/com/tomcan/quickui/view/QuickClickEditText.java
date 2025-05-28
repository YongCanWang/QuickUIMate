package com.tomcan.quickui.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author TomCan
 * @description:
 * @date :2020/4/15 9:05
 */
@Deprecated
public class QuickClickEditText extends AppCompatEditText {
    private final Context                  context;
    private       Drawable                 drawablesLeft;
    private       Drawable                 drawablesRight;
    private       Rect                     drawablesLeftBounds;
    private       Rect                     drawablesRightBounds;
    private       OnDrawablesClickListener onDrawablesClickListener;

    public QuickClickEditText(Context context) {
        this(context, null);
    }

    public QuickClickEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickClickEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setEditFocusable(true);
        drawablesLeft = getCompoundDrawables()[0];
        if (drawablesLeft != null)
            drawablesLeftBounds = drawablesLeft.getBounds();
        if (drawablesLeftBounds == null) drawablesLeftBounds = new Rect(0,0,0,0);
        drawablesRight = getCompoundDrawables()[2];
        if (drawablesRight != null)
            drawablesRightBounds = drawablesRight.getBounds();
        if (drawablesRightBounds == null) drawablesRightBounds = new Rect(0,0,0,0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float down_X = event.getX();
                float down_Y = event.getY();
                if (drawablesLeft != null && drawablesLeftBounds != null && down_X > getPaddingLeft()
                        && down_X < getPaddingLeft() + drawablesLeftBounds.right
                        && down_Y > getPaddingTop() && down_Y < getHeight() - getPaddingBottom()) {
                    setEditFocusable(false);
                    if (onDrawablesClickListener != null)
                        onDrawablesClickListener.leftDrawablesClick();
                } else if (drawablesRight != null && drawablesRightBounds != null && down_X > getWidth() - (drawablesRightBounds.right + getPaddingRight())
                        && down_X < getWidth() - (getPaddingRight())
                        && down_Y > getPaddingTop() && down_Y < getHeight() - getPaddingBottom()) {
                    setEditFocusable(false);
                    if (onDrawablesClickListener != null)
                        onDrawablesClickListener.rightDrawablesClick();
                } else {
                    setEditFocusable(true);
                    if (onDrawablesClickListener != null)
                        onDrawablesClickListener.onClickEditView();
                }
                break;
        }
        return super.onTouchEvent(event);
    }



    public void clear() {
        getText().clear();
    }

    public boolean isTextLengthNull() {
        if (getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setEditFocusable(boolean b) {
        setEnabled(b);
        setFocusable(b);
        setFocusableInTouchMode(b);
        requestFocus();
    }

    public void setDrawablesLeft(int drawable) {
        drawablesLeft = getResources().getDrawable(drawable);
        drawablesLeft.setBounds(0, 0, drawablesLeft.getMinimumWidth(), drawablesLeft.getMinimumHeight());
        setCompoundDrawables(drawablesLeft, null, drawablesRight, null);
    }


    public void setDrawablesRight(int drawable) {
        drawablesRight = getResources().getDrawable(drawable);
        drawablesRight.setBounds(0, 0, drawablesRight.getMinimumWidth(), drawablesRight.getMinimumHeight());
        setCompoundDrawables(drawablesLeft, null, drawablesRight, null);
    }



    public void setOnDrawablesClickListener(OnDrawablesClickListener onDrawablesClickListener) {
        this.onDrawablesClickListener = onDrawablesClickListener;
    }

    public interface OnDrawablesClickListener {
        void onClickEditView();

        void leftDrawablesClick();

        void rightDrawablesClick();
    }


}
