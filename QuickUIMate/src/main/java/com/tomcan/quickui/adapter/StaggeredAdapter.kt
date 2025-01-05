package com.tomcan.quickui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Tom灿
 * @description: 瀑布流
 * @date: 2024/11/10 12:18
 */
abstract class StaggeredAdapter<V, M> : GridAdapter<V, M> {
    private var mContext: Context? = null
    private var mTargetView: View? = null
    private var mBitmap: Bitmap? = null
    private var mWidth: Float? = null
    private var mHeight: Float? = null
    private val STANDARD_SCALE = 1.1 //当图片宽高比例大于STANDARD_SCALE时，采用3:4比例，小于时，则采用1:1比例
    private val SCALE = 4 * 1.0f / 3 //图片缩放比例:3:4显示
    private var mSpace: Int = 0
    private var mLayoutParams: ViewGroup.LayoutParams? = null
    private var mLastHeight = 0

    private constructor()

    private constructor(space: Int) : this() {
        mSpace = space
    }

    constructor(recyclerView: RecyclerView) : super(recyclerView, 0, 0)

    constructor(recyclerView: RecyclerView, spaceHorizontal: Int) : super(
        recyclerView,
        spaceHorizontal,
        0
    )

    constructor(spaceVertical: Int, recyclerView: RecyclerView) : super(
        recyclerView,
        0,
        spaceVertical
    )

    constructor(recyclerView: RecyclerView, spaceHorizontal: Int, spaceVertical: Int) : super(
        recyclerView,
        spaceHorizontal,
        spaceVertical
    )

    private fun setTargetViewStaggered() {
        //计算图片宽高
        var spanCount = getSpanCount(mRecyclerView())
        //  通过RecyclerView宽度计算
        val itemWidth =
            (mRecyclerView().measuredWidth - mSpaceHorizontal() * (spanCount - 1) -
                    (mRecyclerView().paddingLeft + mRecyclerView().paddingRight)) / spanCount
        //  通过屏幕宽度计算
//        val itemWidth = (getScreenWidth(mContext!!) - mSpaceHorizontal() * (spanCount - 1) -
//                (mRecyclerView().marginLeft + mRecyclerView().marginRight)
//                - (mRecyclerView().paddingLeft + mRecyclerView().paddingRight)) / spanCount

        if (mTargetView?.layoutParams is LinearLayout.LayoutParams) {
            mLayoutParams = mTargetView?.layoutParams as LinearLayout.LayoutParams
        } else if (mTargetView?.layoutParams is RelativeLayout.LayoutParams) {
            mLayoutParams = mTargetView?.layoutParams as RelativeLayout.LayoutParams
        } else {
            mLayoutParams = mTargetView?.layoutParams as ViewGroup.LayoutParams
        }
        mLayoutParams?.width = itemWidth
        val scale = (mBitmap?.height?.toFloat()!!) / (mBitmap?.width?.toFloat()!!)
//        val scale = mHeight!! / mWidth!!

//        if (scale > STANDARD_SCALE) {
//            //采用3:4显示
//            mLayoutParams?.height = (itemWidth * SCALE).toInt()
//        } else {
//            //采用1:1显示
//            mLayoutParams?.height = itemWidth
//        }

        // 采用原始比例
        val h = (itemWidth * scale).toInt()
        mLayoutParams?.height = h

        mTargetView?.layoutParams = mLayoutParams

        if (h >= mLastHeight) {
            setLeftRight(0)
        } else {
            setLeftRight(1)
        }
        mLastHeight = h
    }


    private fun setScale() {
        //计算图片宽高
        var spanCount = getSpanCount(mRecyclerView())
        //  通过RecyclerView宽度计算
        val itemWidth =
            (mRecyclerView().measuredWidth - mSpaceHorizontal() * (spanCount - 1) -
                    (mRecyclerView().paddingLeft + mRecyclerView().paddingRight)) / spanCount
        //  通过屏幕宽度计算
//        val itemWidth = (getScreenWidth(mContext!!) - mSpaceHorizontal() * (spanCount - 1) -
//                (mRecyclerView().marginLeft + mRecyclerView().marginRight)
//                - (mRecyclerView().paddingLeft + mRecyclerView().paddingRight)) / spanCount

        if (mTargetView?.layoutParams is LinearLayout.LayoutParams) {
            mLayoutParams = mTargetView?.layoutParams as LinearLayout.LayoutParams
        } else if (mTargetView?.layoutParams is RelativeLayout.LayoutParams) {
            mLayoutParams = mTargetView?.layoutParams as RelativeLayout.LayoutParams
        } else {
            mLayoutParams = mTargetView?.layoutParams as ViewGroup.LayoutParams
        }

        mLayoutParams?.width = itemWidth
        val scale = mHeight!! / mWidth!!
//        if (scale > STANDARD_SCALE) {
//            //采用3:4显示
//            mLayoutParams?.height = (itemWidth * SCALE).toInt()
//        } else {
//            //采用1:1显示
//            mLayoutParams?.height = itemWidth
//        }

        // 采用原始比例
        val h = (itemWidth * scale).toInt()
        mLayoutParams?.height = h

        mTargetView?.layoutParams = mLayoutParams

        if (h >= mLastHeight) {
            setLeftRight(0)
        } else {
            setLeftRight(1)
        }
        mLastHeight = h
    }

    fun setTargetView(): View? {
        return mTargetView
    }

    fun setTarget(): Bitmap? {
        return mBitmap
    }

    fun setTargetView(context: Context, tragetView: View, bitmap: Bitmap) {
        mContext = context
        mTargetView = tragetView
        mBitmap = bitmap
        setTargetViewStaggered()
    }

    fun setTargetView(context: Context, tragetView: View, width: Float, height: Float) {
        mContext = context
        mTargetView = tragetView
        mWidth = width
        mHeight = height
        setScale()
    }

    fun setSpace(space: Int) {
        mSpace = space
    }

    fun getResultWidth() = mLayoutParams?.width

    fun getResultHeight() = mLayoutParams?.height

    private fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    private fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}