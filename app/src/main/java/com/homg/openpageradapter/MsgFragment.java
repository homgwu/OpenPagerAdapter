package com.homg.openpageradapter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends Fragment {
    private static final String KEY_ARGS_NAME = "key_args_name";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MsgEntity> mMsgDatas = new ArrayList<>();
    private RvAdapter mRvAdapter;
    private String mSessionName;

    public MsgFragment() {
        // Required empty public constructor
    }

    public static MsgFragment newInstance(String sessionName) {

        Bundle args = new Bundle();
        args.putString(KEY_ARGS_NAME, sessionName);
        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSessionName = getArguments().getString(KEY_ARGS_NAME);
        return inflater.inflate(R.layout.fragment_msg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvAdapter = new RvAdapter();
        for (int i = 0; i < 51; i++) {
            MsgEntity msgEntity = new MsgEntity();
            msgEntity.name = mSessionName + i;
            mMsgDatas.add(msgEntity);
        }
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.msg_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRvAdapter);
    }

    public void updateData(String name) {
        mSessionName = name;
        for (int i = 0; i < mMsgDatas.size(); i++) {
            MsgEntity msgEntity = mMsgDatas.get(i);
            msgEntity.name = mSessionName + i;
        }
        mRvAdapter.notifyDataSetChanged();
    }

    private class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {

        @NonNull
        @Override
        public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RvViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
            MsgEntity msgEntity = mMsgDatas.get(position);
            TextView textView = (TextView) holder.itemView;
            textView.setText(msgEntity.name);
        }

        @Override
        public int getItemCount() {
            return mMsgDatas.size();
        }

        class RvViewHolder extends RecyclerView.ViewHolder {

            public RvViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
