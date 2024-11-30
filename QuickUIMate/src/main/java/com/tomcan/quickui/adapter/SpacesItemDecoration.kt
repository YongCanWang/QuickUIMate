package com.tomcan.quickui.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/30 20:30
 */
class SpacesItemDecoration : RecyclerView.ItemDecoration {
    var mColumns: Int = 2;
    var mSpace = 0
    var mWidth = 0
    var mHeight = 0
    var mItemHeight = 0
    var mItemWidth = 0

    private var mSpaceHorizontal: Int = 0
    private var mSpaceVertical: Int = 0
    private var spanCount = -1

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

        if (parent.layoutManager is StaggeredGridLayoutManager) {
            if (spanCount == -1) {
                spanCount =
                    (parent.layoutManager as StaggeredGridLayoutManager).spanCount
            }
            val position = parent.getChildAdapterPosition(view)
            /* 绘制垂直边距 */
            if (position >= spanCount) { //第一行之外绘制垂直边距
                outRect.top = mSpaceVertical
            }
            /* 绘制水平边距 */
            val pInLine: Int = position % spanCount // 行内位置
            if (pInLine == 0) {
                outRect.left = 0
            } else {
                outRect.left = (pInLine * 1f * mSpaceHorizontal / spanCount).toInt()
            }
            outRect.right = ((spanCount - pInLine - 1) * 1f * mSpaceHorizontal / spanCount).toInt()
        } else {
            Log.e("GridItemDecoration", "layoutManager isn't StaggeredGridLayoutManager")
        }
    }
}