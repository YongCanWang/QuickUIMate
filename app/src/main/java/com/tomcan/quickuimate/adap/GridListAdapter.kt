package com.tomcan.quickuimate.adap

import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomcan.quickui.adapter.GridAdapter
import com.tomcan.quickui.adapter.StaggeredAdapter
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.bean.Skin.SkinBean
import com.tomcan.quickuimate.databinding.ItemGridListBinding
import com.tomcan.quickuimate.databinding.ItemSkinBinding

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/10 11:11
 */
class GridListAdapter(recyclerView: RecyclerView, spaceHorizontal: Int, spaceVertical: Int) :
    GridAdapter<ItemGridListBinding, SkinBean>(recyclerView, spaceHorizontal, spaceVertical) {

    override fun layout() = R.layout.item_grid_list

    override fun bindData(v: ItemGridListBinding?, m: SkinBean?) {
        v?.skin = m
        v?.ivImag?.context?.let { c ->
            c.assets?.open("skin/" + m?.path).let { b ->
                val mOptions = BitmapFactory.Options()
                mOptions.inScaled = false
//                mOptions.inJustDecodeBounds = false
                mOptions.inSampleSize = 2
                val bitmap = BitmapFactory.decodeStream(b, Rect(), mOptions)!!
                v?.ivImag.let {
                    Glide.with(c)
                        .load(bitmap)
                        .placeholder(R.mipmap.ic_launcher)
//                        .override(getResultWidth()!!, getResultHeight()!!)
//                        .centerCrop()
                        .into(it)
                }
                b?.close()
            }
        }
    }
}