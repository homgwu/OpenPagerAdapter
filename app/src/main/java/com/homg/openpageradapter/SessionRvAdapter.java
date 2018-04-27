package com.homg.openpageradapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by homgwu on 2018/4/27 17:18.
 */
public class SessionRvAdapter extends RecyclerView.Adapter<SessionRvAdapter.SessionVH> {
    private List<SessionEntity> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LayoutInflater mLayoutInflater;

    public SessionRvAdapter(List<SessionEntity> datas, RecyclerView recyclerView) {
        mDatas.clear();
        if (datas != null) mDatas.addAll(datas);
        mRecyclerView = recyclerView;
        mLayoutInflater = LayoutInflater.from(recyclerView.getContext());
    }

    @NonNull
    @Override
    public SessionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_session_item, parent, false);
        return new SessionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionVH holder, int position) {
        SessionEntity sessionEntity = mDatas.get(position);
        sessionEntity.position = position;
        holder.mNameTv.setText(sessionEntity.name);
        if (sessionEntity.isSelected) {
            holder.mNameTv.setTextColor(Color.BLUE);
        } else {
            holder.mNameTv.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public SessionEntity getItem(int position) {
        if (position < 0 || position >= mDatas.size()) return null;
        return mDatas.get(position);
    }

    public void addData(int position, SessionEntity sessionEntity) {
        mDatas.add(position, sessionEntity);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size());
        compatibilityDataSizeChanged(1);
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void moveData(int from, int to) {
        if (from == to) return;
        Collections.swap(mDatas, from, to);
        int start = 0;
        if (from > to) start = to;
        else start = from;
        int count = Math.abs(from - to) + 1;
        notifyItemRangeChanged(start, count);
        mRecyclerView.smoothScrollToPosition(to);
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(position, mDatas.size() - position);
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mDatas.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    static class SessionVH extends RecyclerView.ViewHolder {
        private TextView mNameTv;

        public SessionVH(View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.session_name_tv);
        }
    }
}
