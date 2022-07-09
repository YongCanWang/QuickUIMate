package com.tomcan.quickuimate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TomCan
 * @description:
 * @date :2021/11/3 14:50
 */
public abstract class QuickRecyclerViewAdapter<M, V> extends
        RecyclerView.Adapter<QuickRecyclerViewAdapter.QuickViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private final ArrayList<M> datas = new ArrayList<>();
    private static final SparseArrayCompat<View> headViews = new SparseArrayCompat<>();
    private static final SparseArrayCompat<View> footViews = new SparseArrayCompat<>();
    private static int VIEW_HEAD_INDEX = 100000;
    private static int VIEW_FOOT_INDEX = 200000;
    private static OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private static OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;

    @NonNull
    @Override
    public QuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (null != headViews.get(viewType)) {
            return new QuickViewHolder(headViews.get(viewType));
        } else if (null != footViews.get(viewType)) {
            return new QuickViewHolder(footViews.get(viewType));
        }
        return new QuickViewHolder(getDataBinding(parent.getContext(), parent, setItemLayoutID()));
    }


    @Override
    public void onBindViewHolder(@NonNull QuickRecyclerViewAdapter.QuickViewHolder holder, int position) {
        if (isShowViewHead(position)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(layoutParams);
        } else if (isShowViewFoot(position)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
            bindData((V) holder.v, datas.get(position - headViews.size()));
        }
    }

    @Override
    public int getItemCount() {
        return null == datas || datas.size() == 0 ? 0 : datas.size() + headViews.size() + footViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowViewHead(position)) return headViews.keyAt(position);
        if (isShowViewFoot(position))
            return footViews.keyAt(Math.abs(position - (datas.size() + headViews.size())));
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        if (null != onRecyclerViewItemClickListener)
            onRecyclerViewItemClickListener.itemView(v, position);
    }

    @Override
    public boolean onLongClick(View v) {
        Integer position = (Integer) v.getTag();
        if (null != onRecyclerViewItemLongClickListener)
            return onRecyclerViewItemLongClickListener.itemView(v, position);
        return true;
    }

    public class QuickViewHolder extends RecyclerView.ViewHolder {
        public V v;

        public QuickViewHolder(ViewDataBinding v) {
            this(v.getRoot());
            this.v = (V) v;
        }

        public QuickViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void updateData(List<M> datas) {
        if (null == datas) return;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addViewHead(View headView) {
        headViews.put(headViews.size() + VIEW_HEAD_INDEX, headView);
    }

    public void addFootView(View footView) {
        footViews.put(footViews.size() + VIEW_FOOT_INDEX, footView);
    }

    private boolean isShowViewHead(int position) {
        return position < headViews.size();
    }

    private boolean isShowViewFoot(int position) {
        return position >= datas.size() + headViews.size();
    }

    public void clear() {
        VIEW_HEAD_INDEX = 100000;
        VIEW_FOOT_INDEX = 200000;
        headViews.clear();
        footViews.clear();
    }


    private ViewDataBinding getDataBinding(Context context, ViewGroup parent, int layoutId) {
        return DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false);
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public abstract int setItemLayoutID();

    public abstract void bindData(V v, M m);


    public void setOnRecyclerViewItemClickListener(QuickRecyclerViewAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(QuickRecyclerViewAdapter.OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void itemView(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        boolean itemView(View view, int position);
    }

}
