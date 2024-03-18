package com.tomcan.quickui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TomCan
 * @description:
 * @date :2021/4/16 10:34
 */
public abstract class NaviBaseAdapter_Copy<M, V> extends BaseAdapter {
    private final List<M>             datas = new ArrayList<>();
    public        OnItemClickListener onItemClickListener;

    public NaviBaseAdapter_Copy() {
        this(null);
    }

    public NaviBaseAdapter_Copy(List<M> datas) {
        if (null != datas) this.datas.addAll(datas);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public M getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            ViewDataBinding dataBinding = getDataBinding(parent.getContext(), parent, setLayoutId());
            viewHolder = new ViewHolder(dataBinding);
            convertView = dataBinding.getRoot();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindData((V) viewHolder.v, getItem(position));
        return convertView;
    }


    public void updateData(List<M> datas) {
        if (null == datas) return;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public List<M> getListDatas() {
        return datas;
    }

    public class ViewHolder {
        public V v;

        public ViewHolder(ViewDataBinding v) {
            this.v = (V) v;
        }
    }

    private ViewDataBinding getDataBinding(Context context, ViewGroup parent, int layoutId) {
        return DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false);
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface OnItemClickListener<T> {
        void itemClick(T t);

        void itemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public abstract int setLayoutId();

    public abstract void bindData(V v, M m);


}
