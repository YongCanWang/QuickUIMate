package com.tomcan.quickuimate.adap

import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomcan.quickui.adapter.StaggeredAdapter
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.bean.InfoBean
import com.tomcan.quickuimate.databinding.ItemWaterfallBinding

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/10 11:11
 */
class WaterfallAdapter(recyclerView: RecyclerView) :
    StaggeredAdapter<ItemWaterfallBinding, InfoBean>(recyclerView) {

    override fun layout() = R.layout.item_waterfall

    override fun bindData(v: ItemWaterfallBinding?, m: InfoBean?) {
        v?.info = m
        v?.ivImag?.context?.let { c ->
            c.assets?.open("imag/" + m?.path).let { b ->
                val mOptions = BitmapFactory.Options()
                mOptions.inScaled = false
                val bitmap = BitmapFactory.decodeStream(b, Rect(), mOptions)!!
                v?.ivImag.let {
                    setTargetView(c, it, bitmap)
//                    setTargetView(c, it, bitmap.width.toFloat(), bitmap.height.toFloat())
                    Glide.with(c)
                        .load(bitmap)
                        .placeholder(R.mipmap.ic_launcher)
                        .override(getResultWidth()!!, getResultHeight()!!)
                        .centerCrop()
                        .into(it)
                }
                b?.close()
            }
        }
    }
}