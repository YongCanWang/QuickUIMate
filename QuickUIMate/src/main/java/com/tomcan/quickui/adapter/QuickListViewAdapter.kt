package com.tomcan.quickui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author Tom灿
 * @description:
 * @date: 2025/1/4 19:17
 */
abstract class QuickListViewAdapter<V, M> : BaseAdapter() {

    companion object {
        class ViewHolder {
            constructor(v: ViewDataBinding) {
                this.v = v
            }

            var v: ViewDataBinding
        }
    }

    private val mDatas: ArrayList<M> = ArrayList()

    override fun getCount() = if (mDatas.isEmpty()) {
        0
    } else mDatas.size

    override fun getItem(p0: Int): M {
        return mDatas[p0]
    }

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        var viewHolder: ViewHolder? = null
        view?.let {
            viewHolder = it.tag as ViewHolder
        } ?: kotlin.run {
            p2?.let {
                val dataBinding = getDataBinding(it.context, it, layout())
                view = dataBinding.root
                viewHolder = ViewHolder(dataBinding);
                view?.tag = viewHolder
            }
        }

        bindData(viewHolder?.v as V, mDatas[p0])
        return view!!
    }

    abstract fun layout(): Int

    abstract fun bindData(v: V, m: M)

    private fun getDataBinding(
        context: Context,
        parent: ViewGroup,
        layoutId: Int
    ): ViewDataBinding {
        return DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false)
    }

    private fun getLayoutInflater(context: Context): LayoutInflater {
        return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun addData(data: M) {
        mDatas.add(data)
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<M>) {
        mDatas.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: M) {
        mDatas.clear()
        mDatas.add(data)
        notifyDataSetChanged()
    }

    fun updateData(data: ArrayList<M>) {
        mDatas.clear()
        mDatas.addAll(data)
        notifyDataSetChanged()
    }
}