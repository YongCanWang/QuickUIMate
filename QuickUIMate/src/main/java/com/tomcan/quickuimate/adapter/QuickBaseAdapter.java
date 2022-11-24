package com.tomcan.quickuimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;

import java.util.List;

/**
 * @author TomCan
 * @description:
 * @date :2021/4/16 10:34
 */
public abstract class QuickBaseAdapter<V, M> extends BaseAdapter {
    private final ObservableArrayList<M> observableArrayList = new ObservableArrayList<>();
    public OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public QuickBaseAdapter() {
    }

    public QuickBaseAdapter(List<M> dataList) {
        updateData(dataList);
    }

    @Override
    public int getCount() {
        return observableArrayList == null ? 0 : observableArrayList.size();
    }

    @Override
    public M getItem(int position) {
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
            ViewDataBinding dataBinding = getDataBinding(parent.getContext(), parent, setLayoutId());
            viewHolder = new ViewHolder(dataBinding);
            convertView = dataBinding.getRoot();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        M m = getItem(position);
        viewHolder.setPosition(position);
        viewHolder.setM(m);
        bindData((V) viewHolder.v, m);
        return convertView;
    }


    public abstract int setLayoutId();


    public abstract void bindData(V v, M m);


    public void updateData(List<M> datas) {
        if (null == datas) return;
        this.observableArrayList.clear();
        this.observableArrayList.addAll(datas);
        notifyDataSetChanged();
    }

    public ObservableArrayList<M> getDatas() {
        return observableArrayList;
    }

    private class ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private V v;
        private M m;
        private int position;

        public ViewHolder(ViewDataBinding v) {
            this.v = (V) v;
            v.getRoot().setOnClickListener(this);
            v.getRoot().setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) onItemClickListener.onItemClick(m, view, position);
        }

        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) onItemLongClickListener.onLongClick(m);
            return false;
        }

        private void setM(M m) {
            this.m = m;
        }

        private void setPosition(int position) {
            this.position = position;
        }
    }

    public interface OnItemClickListener<M> {
        void onItemClick(M m, View viwe, int position);
    }

    public interface OnItemLongClickListener<M> {
        void onLongClick(M m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    private ViewDataBinding getDataBinding(Context context, ViewGroup parent, int layoutId) {
        return DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false);
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
