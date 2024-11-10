package com.tomcan.quickui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author Tom灿
 * @description: 瀑布流
 * @date: 2024/11/10 12:18
 */
abstract class StaggeredAdapter<V, M> : BaseAdapter<V, M>() {
    private var mContext: Context? = null
    private var mTargetView: View? = null
    private var mBitmap: Bitmap? = null
    private var mWidth: Float? = null
    private var mHeight: Float? = null
    private val STANDARD_SCALE = 1.1 //当图片宽高比例大于STANDARD_SCALE时，采用3:4比例，小于时，则采用1:1比例
    private val SCALE = 4 * 1.0f / 3 //图片缩放比例
    private var mSpace: Int = 20
    private var mLayoutParams: LinearLayout.LayoutParams? = null

    private fun setTargetViewStaggered() {
        //计算图片宽高
        val itemWidth = (getScreenWidth(mContext!!) - mSpace) / 2
        mLayoutParams = mTargetView?.layoutParams as LinearLayout.LayoutParams
        mLayoutParams?.width = itemWidth
        val scale = (mBitmap?.height?.toFloat()!!) / (mBitmap?.width?.toFloat()!!)
//        val scale = mHeight!! / mWidth!!
        if (scale > STANDARD_SCALE) {
            //采用3:4显示
            mLayoutParams?.height = (itemWidth * SCALE).toInt()
        } else {
            //采用1:1显示
            mLayoutParams?.height = itemWidth
        }
        mTargetView?.layoutParams = mLayoutParams
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
        setTargetViewStaggered()
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

    private class StaggeredItemDecoration : ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            super.getItemOffsets(outRect, itemPosition, parent)
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}