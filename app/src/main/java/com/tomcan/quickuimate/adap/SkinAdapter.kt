package com.tomcan.quickuimate.adap

import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomcan.quickui.adapter.StaggeredAdapter
import com.tomcan.quickuimate.R
import com.tomcan.quickuimate.bean.Skin.SkinBean
import com.tomcan.quickuimate.databinding.ItemSkinBinding

/**
 * @author Tom灿
 * @description:
 * @date: 2024/11/10 11:11
 */
class SkinAdapter(recyclerView: RecyclerView, spaceHorizontal: Int, spaceVertical: Int) :
    StaggeredAdapter<ItemSkinBinding, SkinBean>(recyclerView, spaceHorizontal, spaceVertical) {

    override fun layout() = R.layout.item_skin

    override fun bindData(v: ItemSkinBinding?, m: SkinBean?) {
        v?.skin = m
        v?.ivImag?.context?.let { c ->
            c.assets?.open("skin/" + m?.path).let { b ->
                val mOptions = BitmapFactory.Options()
                mOptions.inScaled = false
//                mOptions.inJustDecodeBounds = false
                mOptions.inSampleSize = 1
                val bitmap = BitmapFactory.decodeStream(b, Rect(), mOptions)!!
                v?.ivImag.let {
                    setTargetView(c, it, bitmap)
//                    setTargetView(c, it, bitmap.width.toFloat(), bitmap.height.toFloat())
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