package com.tomcan.quickuimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author TomCan
 * @description:
 * @date :2021/4/16 10:34
 */
public class NaviBaseAdapter<T, V> extends BaseAdapter {
    public final Context context;
    public List<T> dataList;
    public final LayoutInflater layoutInflater;

    public NaviBaseAdapter(Context context) {
        this(context, null);
    }

    public NaviBaseAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getListDatas() {
        return dataList;
    }


    public class ViewHolder {
        public V v;

        public ViewHolder(V v) {
            this.v = v;
        }
    }
}
