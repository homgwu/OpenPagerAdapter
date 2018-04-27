package com.homg.openpageradapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Button mAddBtn, mUpdateBtn, mDeleteBtn, mMoveBtn;
    private EditText mUpdateEt, mFromEt, mToEt, mUpdateContentEt, mDeleteEt;
    private RecyclerView mSessionRv;
    private ViewPager mViewPager;
    private MyOpenPagerAdapter mAdapter;

    private LinearLayoutManager mSessionLinearLayoutManager;
    private SessionRvAdapter mSessionRvAdapter;
    private SessionEntity mSelectedSession;

    private String[] mSessionNameArray = new String[]{"张飞", "关羽", "黄忠", "典韦", "徐庶", "马岱", "郭嘉", "荀彧", "曹操", "孙权"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddBtn = findViewById(R.id.add_btn);
        mUpdateBtn = findViewById(R.id.update_btn);
        mDeleteBtn = findViewById(R.id.delete_btn);
        mMoveBtn = findViewById(R.id.move_btn);
        mAddBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mMoveBtn.setOnClickListener(this);
        mUpdateEt = findViewById(R.id.update_et);
        mDeleteEt = findViewById(R.id.delete_et);
        mFromEt = findViewById(R.id.from_et);
        mToEt = findViewById(R.id.to_et);
        mUpdateContentEt = findViewById(R.id.update_content_et);
        mSessionRv = findViewById(R.id.session_rv);
        mViewPager = findViewById(R.id.content_vp);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.name = mSessionNameArray[0];
        sessionEntity.isSelected = true;
        mSelectedSession = sessionEntity;
        List<SessionEntity> datas = new ArrayList<>();
        datas.add(sessionEntity);
        mAdapter = new MyOpenPagerAdapter(getSupportFragmentManager(), datas);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);

        mSessionLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mSessionRv.setHasFixedSize(true);
        mSessionRv.setLayoutManager(mSessionLinearLayoutManager);
        mSessionRvAdapter = new SessionRvAdapter(datas, mSessionRv);
        mSessionRv.setAdapter(mSessionRvAdapter);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.add_btn:
                SessionEntity sessionEntity = getNewSession();
                if (sessionEntity != null) {
                    mSessionRvAdapter.addData(0, sessionEntity);
                    mAdapter.addData(0, sessionEntity);
                } else {
                    Toast.makeText(this, "no more", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_btn:
                try {
                    int position = Integer.parseInt(mUpdateEt.getText().toString());
                    if (position >= 0 && position < mAdapter.getCount()) {
                        String name = mUpdateContentEt.getText().toString();
                        mSessionRvAdapter.getItem(position).name = name;
                        mSessionRvAdapter.notifyItemChanged(position);

                        MsgFragment msgFragment = mAdapter.getCachedFragmentByPosition(position);
                        if (msgFragment != null) msgFragment.updateData(name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.delete_btn:
                try {
                    int position = Integer.parseInt(mDeleteEt.getText().toString());
                    if (position >= 0 && position < mAdapter.getCount()) {
                        mSessionRvAdapter.remove(position);
                        mAdapter.remove(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.move_btn:
                try {
                    int from = Integer.parseInt(mFromEt.getText().toString());
                    int to = Integer.parseInt(mToEt.getText().toString());
                    if (from >= 0 && from < mAdapter.getCount() && to >= 0 && to < mAdapter.getCount()) {
                        mSessionRvAdapter.moveData(from, to);
                        mAdapter.moveData(from, to);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private SessionEntity getNewSession() {
        for (int i = 0; i < mSessionNameArray.length; i++) {
            String name = mSessionNameArray[i];
            SessionEntity sessionEntity = new SessionEntity();
            sessionEntity.name = name;
            if (mAdapter.getDataPosition(sessionEntity) < 0) {
                return sessionEntity;
            }
        }
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SessionEntity sessionEntity = mSessionRvAdapter.getItem(position);
        if (sessionEntity != mSelectedSession) {
            sessionEntity.isSelected = true;
            if (mSelectedSession != null) {
                mSelectedSession.isSelected = false;
                mSessionRvAdapter.notifyItemChanged(mSelectedSession.position);
            }
            mSessionRvAdapter.notifyItemChanged(position);
            mSelectedSession = sessionEntity;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
