package com.tomcan.quickui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author TomCan
 * @description:
 * @date :2020/11/20 10:59
 */
@Deprecated
public class QuickCommentView extends ViewGroup {


    private final Context context;
    private Button button;
    private TextView textView;

    public QuickCommentView(Context context) {
        this(context, null);
    }

    public QuickCommentView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public QuickCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public QuickCommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (button == null)
            button = new Button(context);
        if (textView == null)
            textView = new TextView(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        button.setText("发布");
        button.layout(getMeasuredWidth() - button.getMeasuredWidth(),
                getMeasuredHeight() - button.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());

        textView.setText("-200");
        button.layout(0,
                getMeasuredHeight() - button.getMeasuredHeight(), button.getMeasuredWidth(), getMeasuredHeight());
    }
}
