package com.tomcan.quickui.adapter;

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
 * @date :2021/4/16 10:34
 */
public abstract class QuickAdapter<V, M> extends
        RecyclerView.Adapter<QuickAdapter.QuickViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private final ArrayList<M> datas = new ArrayList<>();
    private final SparseArrayCompat<View> headViews = new SparseArrayCompat<>();
    private final SparseArrayCompat<View> footViews = new SparseArrayCompat<>();
    private int VIEW_HEAD_INDEX = 100000;
    private int VIEW_FOOT_INDEX = 200000;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener;
    private boolean isShowLoadingView = false;

    @NonNull
    @Override
    public QuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (null != headViews.get(viewType)) {
            return new QuickViewHolder(headViews.get(viewType));
        } else if (null != footViews.get(viewType)) {
            return new QuickViewHolder(footViews.get(viewType));
        }
        return new QuickViewHolder(getDataBinding(parent.getContext(), parent, layout()));
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, int position) {
        if (isShowViewHead(position)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(layoutParams);
        } else if (isShowViewFoot(position)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(layoutParams);
        } else if (isShowLoadingView(position)) {

        } else {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
            bindData((V) holder.v, datas.get(position - headViews.size()));
        }
    }

    @Override
    public int getItemCount() {
        int headFootViewsSize = headViews.size() + footViews.size();
        int dataHeadFootViewsSize = datas.size() + headFootViewsSize;
        if (isShowLoadingView)
            return null == datas || datas.size() == 0 ? headFootViewsSize
                    + 1 : dataHeadFootViewsSize + 1;
        else
            return null == datas || datas.size() == 0 ? headFootViewsSize : dataHeadFootViewsSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowViewHead(position)) return headViews.keyAt(headViews.size() - 1 - position);
        if (isShowViewFoot(position))
            return footViews.keyAt(Math.abs(position - (datas.size() + headViews.size())));
        if (isShowLoadingView(position)) return datas.size() + headViews.size() + footViews.size();
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        QuickViewHolder holder = (QuickViewHolder) v.getTag();
        if (null != onRecyclerViewItemClickListener)
            onRecyclerViewItemClickListener.itemView(v, holder.getLayoutPosition() - headViews.size());
    }

    @Override
    public boolean onLongClick(View view) {
        QuickViewHolder holder = (QuickViewHolder) view.getTag();
        if (null != mOnRecyclerViewItemLongClickListener) {
            mOnRecyclerViewItemLongClickListener.itemView(view, holder.getLayoutPosition() - headViews.size());
        }
        return false;
    }

    public static class QuickViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding v;

        public QuickViewHolder(ViewDataBinding v) {
            this(v.getRoot());
            this.v = (ViewDataBinding) v;
        }

        public QuickViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void addData(List<M> datas) {
        if (null == datas) return;
        int size = this.datas.size();
        this.datas.addAll(datas);
        notifyItemRangeInserted(size + headViews.size(), datas.size());
    }

    public void addData(M data) {
        if (null == data) return;
        this.datas.add(data);
        notifyItemInserted(datas.size() + headViews.size());
    }

    public void addData(int position, M data) {
        if (null == data) return;
        this.datas.add(position, data);
        notifyItemInserted(position + headViews.size());
    }

    public void addData(List<M> datas, int positionStart, int itemCount) {
        if (null == datas || positionStart < 0 || positionStart > this.datas.size()) return;
        ArrayList<M> insertedDatas = new ArrayList<>();
        for (int i = positionStart; i < positionStart + itemCount; i++) {
            insertedDatas.add(datas.get(i));
        }
        this.datas.addAll(positionStart, insertedDatas);
        notifyItemRangeInserted(positionStart + headViews.size(), itemCount);
    }

    public void updateData(List<M> datas) {
        if (null == datas) return;
        this.datas.clear();
        notifyDataSetChanged();
        this.datas.addAll(datas);
        notifyItemRangeChanged(headViews.size(), datas.size());
    }

    public void updateData(int position, M data) {
        if (null == data || position < 0 || position >= datas.size()) return;
        this.datas.set(position, data);
        notifyItemChanged(position + headViews.size(), data);
    }

    public void removeData(int position) {
        if (position < 0 || position >= datas.size()) return;
        datas.remove(position);
        notifyItemRemoved(position + headViews.size());
        notifyItemRangeChanged(position + headViews.size(), 1);
    }

    public void removeData(int positionStart, int itemCount) {
        if (positionStart < 0 || positionStart >= datas.size()) return;
        ArrayList<M> deletes = new ArrayList<>();
        for (int i = positionStart; i < positionStart + itemCount; i++) {
            deletes.add(datas.get(i));
        }
        datas.removeAll(deletes);
        notifyItemRangeRemoved(positionStart + headViews.size(), itemCount);
    }

    public void removeData(List<M> datas) {
        if (null == datas) return;
        int size = this.datas.size();
        this.datas.removeAll(datas);
        notifyItemRangeRemoved(headViews.size(), size);
        notifyItemRangeChanged(headViews.size(), datas.size());
    }

    public void clear() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    public void reset() {
        VIEW_HEAD_INDEX = 100000;
        VIEW_FOOT_INDEX = 200000;
        headViews.clear();
        footViews.clear();
        clear();
    }

    public void addHeadView(View headView) {
        headViews.put(headViews.size() + VIEW_HEAD_INDEX, headView);
    }

    public void addFootView(View footView) {
        footViews.put(footViews.size() + VIEW_FOOT_INDEX, footView);
    }

    public void removeLastHeadView() {
        headViews.remove(headViews.size() - 1 + VIEW_HEAD_INDEX);
    }

    public void removeAllHeadView() {
        headViews.clear();
    }

    public void removeLastFootView() {
        footViews.remove(footViews.size() - 1 + VIEW_FOOT_INDEX);
    }

    private boolean isShowViewHead(int position) {
        return position < headViews.size();
    }

    private boolean isShowViewFoot(int position) {
        return position >= datas.size() + headViews.size();
    }

    private boolean isShowLoadingView(int position) {
        return position >= datas.size() + headViews.size() + footViews.size();
    }

    public void setIsShowLoadingView(boolean isShow) {
        this.isShowLoadingView = isShow;
    }

    private ViewDataBinding getDataBinding(Context context, ViewGroup parent, int layoutId) {
        return DataBindingUtil.inflate(getLayoutInflater(context), layoutId, parent, false);
    }

    private LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public abstract int layout();

    public abstract void bindData(V v, M m);

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.mOnRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void itemView(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        boolean itemView(View view, int position);
    }
}
