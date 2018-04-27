package com.homg.openpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.homg.openpageradapterlib.OpenPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by homgwu on 2018/4/27 10:03.
 */
public class MyOpenPagerAdapter extends OpenPagerAdapter<SessionEntity> {
    private List<SessionEntity> mDatas = new ArrayList<>();

    public MyOpenPagerAdapter(FragmentManager fm, List<SessionEntity> datas) {
        super(fm);
        mDatas.clear();
        if (datas != null) mDatas.addAll(datas);
    }

    @Override
    public Fragment getItem(int position) {
        return MsgFragment.newInstance(mDatas.get(position).name);
    }

    @Override
    protected SessionEntity getItemData(int position) {
        return mDatas.get(position);
    }

    @Override
    protected boolean dataEquals(SessionEntity oldData, SessionEntity newData) {
        return oldData.equals(newData);
    }

    @Override
    public int getDataPosition(SessionEntity data) {
        return mDatas.indexOf(data);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public MsgFragment getCurrentFragmentItem() {
        return (MsgFragment) getCurrentPrimaryItem();
    }

    public void setNewData(List<SessionEntity> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(SessionEntity sessionEntity) {
        mDatas.add(sessionEntity);
        notifyDataSetChanged();
    }

    public void addData(int position, SessionEntity sessionEntity) {
        mDatas.add(position, sessionEntity);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void moveData(int from, int to) {
        if (from == to) return;
        Collections.swap(mDatas, from, to);
        notifyDataSetChanged();
    }

    public void moveDataToFirst(int from) {
        SessionEntity tempData = mDatas.remove(from);
        mDatas.add(0, tempData);
        notifyDataSetChanged();
    }

    public void updateByPosition(int position, SessionEntity sessionEntity) {
        if (position >= 0 && mDatas.size() > position) {
            mDatas.set(position, sessionEntity);
            MsgFragment targetF = getCachedFragmentByPosition(position);
            if (targetF != null) {
                targetF.updateData(sessionEntity.name);
            }
        }
    }

    public MsgFragment getCachedFragmentByPosition(int position) {
        return (MsgFragment) getFragmentByPosition(position);
    }

}
