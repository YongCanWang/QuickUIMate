package com.tomcan.quickuimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;

import java.util.List;

/**
 * @author TomCan
 * @description:
 * @date :2021/4/16 10:34
 */
public abstract class QuickBaseAdapter<T, V> extends android.widget.BaseAdapter {
    public        Context                context;
    private final ObservableArrayList<T> observableArrayList = new ObservableArrayList<>();
    public        LayoutInflater         layoutInflater;

    public QuickBaseAdapter() {
    }

    public QuickBaseAdapter(Context context) {
        this(context, null);
    }

    public QuickBaseAdapter(List<T> dataList) {
        this(null, dataList);
    }

    public QuickBaseAdapter(Context context, List<T> dataList) {
        this.context = context;
        if (null != dataList) this.observableArrayList.addAll(dataList);
        if (null != context)
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return observableArrayList == null ? 0 : observableArrayList.size();
    }

    @Override
    public T getItem(int position) {
        return observableArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            V v = getDataBinding(parent.getContext(), parent, setLayoutId());
            viewHolder = new ViewHolder(v);
            convertView = getView(viewHolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setData(viewHolder, position);
        return convertView;
    }


    public abstract int setLayoutId();

    public abstract View getView(ViewHolder viewHolder);

    public abstract void setData(ViewHolder viewHolder, int position);


    public void update(List<T> dataList) {
        if (null == dataList) return;
        this.observableArrayList.clear();
        this.observableArrayList.addAll(dataList);
        notifyDataSetChanged();
    }

    public ObservableArrayList<T> getListDatas() {
        return observableArrayList;
    }

    public class ViewHolder {
        public V v;

        public ViewHolder(V v) {
            this.v = v;
        }
    }


    private V getDataBinding(Context context, ViewGroup parent, int layoutId) {
        // TODO 获取DataBinding对象涉及类的强制转换，考虑是否需要重写给子类，在子类中完成（纠结...(ー`´ー)）。
        return (V) DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false);
    }

    public LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
