package com.tomcan.quickui.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tomcan.quickui.utils.Utils

/**
 * @author Tom灿
 * @description:  item交替摆放间距无问题，当一列出现连续排放item间距设置会出现错。
 *                不能用position索引来判断itemView的位置。需要优化
 * @date: 2024/11/30 22:32
 */
abstract class GridAdapter<V, M>() : BaseAdapter<V, M>() {
    private lateinit var mRecyclerView: RecyclerView
    private var mSpaceHorizontal: Int = 0
    private var mSpaceVertical: Int = 0
    private lateinit var mSpacesItemDecoration: SpacesItemDecoration

    constructor(recyclerView: RecyclerView) : this(recyclerView, 0, 0)

    constructor(recyclerView: RecyclerView, spaceHorizontal: Int) : this(
        recyclerView,
        spaceHorizontal,
        0
    )

    constructor(spaceVertical: Int, recyclerView: RecyclerView) : this(
        recyclerView,
        0,
        spaceVertical
    )

    constructor(recyclerView: RecyclerView, spaceHorizontal: Int, spaceVertical: Int) : this() {
        this.mRecyclerView = recyclerView
        mSpaceHorizontal = Utils.dp2px(mRecyclerView.context, spaceHorizontal.toFloat())
        mSpaceVertical = Utils.dp2px(mRecyclerView.context, spaceVertical.toFloat())
        mSpacesItemDecoration = SpacesItemDecoration(mSpaceHorizontal, mSpaceVertical)
        mRecyclerView.addItemDecoration(mSpacesItemDecoration)
    }

    private class SpacesItemDecoration : RecyclerView.ItemDecoration {
        var mColumns: Int = 2
        var mSpace = 0
        var mWidth = 0
        var mHeight = 0
        var mItemHeight = 0
        var mItemWidth = 0

        private var mSpaceHorizontal: Int = 0
        private var mSpaceVertical: Int = 0
        private var mSpanCount = -1
        private var mIsLeftRight = 0 // 0交替摆放，1继续摆放
        private var mLastHeight = 0

        private constructor()

        constructor(spaceHorizontal: Int, spaceVertical: Int) {
            mSpaceHorizontal = spaceHorizontal
            mSpaceVertical = spaceVertical
        }

        private constructor(
            space: Int,
            height: Int,
            width: Int,
            itemHeight: Int,
            itemWidth: Int,
            columns: Int
        ) : this() {
            mSpace = space
            mWidth = width
            mHeight = height
            mItemWidth = itemWidth
            mItemHeight = itemHeight
            mColumns = columns
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {

//        val position = parent.getChildAdapterPosition(view)
//        val totalCount = parent.adapter!!.itemCount
//
//        outRect.top = mSpace / 2
//        outRect.bottom = mSpace / 2
//
//        val w: Double = (width.toDouble() - mItemWidth * mColumns) / (mColumns * (mColumns - 1))
//        val p = position % mColumns
//        outRect.left = (w * p).toInt()
//
//
//        // 最上面的一排（mColumns个item）
//        if (position < mColumns) outRect.top = 0
//
//        // 最下面一排
//        val tem = (ceil(totalCount.toDouble() / mColumns) * mColumns).toInt()
//        if (position >= tem - mColumns) outRect.bottom = 0

            if (mSpanCount == -1) {
                mSpanCount = getSpanCount(parent)
                if (mSpanCount == -1) {
                    Log.e(
                        "GridItemDecoration",
                        "layoutManager isn't GridLayoutManager、StaggeredGridLayoutManager"
                    )
                }
            }
            if (mSpanCount == -1) return
            val position = parent.getChildAdapterPosition(view)
            /* 绘制垂直边距 */
            if (position >= mSpanCount) { //第一行之外绘制垂直边距
                outRect.top = mSpaceVertical
            }
            /* 绘制水平边距 */
            val pInLine: Int = position % mSpanCount // 行内位置
            Log.e("tomcan", "getItemOffsets: $position---$pInLine")
            if (pInLine == 0) {
                outRect.left = 0
            } else {
                outRect.left = (pInLine * 1f * mSpaceHorizontal / mSpanCount).toInt()
            }
            outRect.right =
                ((mSpanCount - pInLine - 1) * 1f * mSpaceHorizontal / mSpanCount).toInt()
            mLastHeight = view.height
        }

        fun setLeftRight(leftRight: Int) {
            mIsLeftRight = leftRight
        }


//        private fun getSpanCount(parent: RecyclerView): Int =
//            when (parent.layoutManager) {
//                is GridLayoutManager -> {
//                    (parent.layoutManager as GridLayoutManager).spanCount
//                }
//
//                is StaggeredGridLayoutManager -> {
//                    (parent.layoutManager as StaggeredGridLayoutManager).spanCount
//                }
//
//                else -> {
//                    -1
//                }
//            }
    }

    companion object {
        fun getSpanCount(parent: RecyclerView): Int =
            when (parent.layoutManager) {
                is GridLayoutManager -> {
                    (parent.layoutManager as GridLayoutManager).spanCount
                }

                is StaggeredGridLayoutManager -> {
                    (parent.layoutManager as StaggeredGridLayoutManager).spanCount
                }

                else -> {
                    -1
                }
            }
    }

    fun setLeftRight(leftRight: Int) {
        mSpacesItemDecoration.setLeftRight(leftRight)
    }

    fun mRecyclerView() = mRecyclerView

    fun mSpaceHorizontal() = mSpaceHorizontal

    fun mSpaceVertical() = mSpaceVertical
}