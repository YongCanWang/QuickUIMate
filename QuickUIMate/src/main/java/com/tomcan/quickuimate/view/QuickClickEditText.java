package com.tomcan.quickuimate.view;

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
public class QuickClickEditText extends AppCompatEditText {
    private final Context                  context;
    private       Drawable                 drawablesLeft;
    private       Drawable                 drawablesRight;
    private       Rect                     drawablesRightLeft;
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
            drawablesRightLeft = drawablesLeft.getBounds();
        drawablesRight = getCompoundDrawables()[2];
        if (drawablesRight != null)
            drawablesRightBounds = drawablesRight.getBounds();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float down_X = event.getX();
                float down_Y = event.getY();
                if (drawablesLeft != null && down_X > getPaddingLeft()
                        && down_X < getPaddingLeft() + drawablesRightLeft.right
                        && down_Y > getPaddingTop() && down_Y < getHeight() - getPaddingBottom()) {
                    setEditFocusable(false);
                    if (onDrawablesClickListener != null)
                        onDrawablesClickListener.leftDrawablesClick();
                } else if (drawablesRight != null && down_X > getWidth() - (drawablesRightBounds.right + getPaddingRight())
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

    public void setOnDrawablesClickListener(OnDrawablesClickListener onDrawablesClickListener) {
        this.onDrawablesClickListener = onDrawablesClickListener;
    }

    public interface OnDrawablesClickListener {
        void onClickEditView();

        void leftDrawablesClick();

        void rightDrawablesClick();
    }


}
