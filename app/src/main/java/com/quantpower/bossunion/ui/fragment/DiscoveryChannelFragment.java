package com.quantpower.bossunion.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.utils.CustomLinearLayoutManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/7/21.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class DiscoveryChannelFragment extends BaseFragment {
    private static final String ARG_POSITION = "position";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CustomLinearLayoutManager linearLayoutManager;
    private RecyclerAdapter myCapitalReycAdapter;
    private List<String> myCapitalReycList;

    public static DiscoveryChannelFragment newInstance(int position) {
        DiscoveryChannelFragment newsChannelFragment = new DiscoveryChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        newsChannelFragment.setArguments(bundle);
        return newsChannelFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_discovery_channel;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.bindRecycleView();
    }

    private void bindRecycleView() {
        this.getData();
        this.myCapitalReycAdapter = new RecyclerAdapter<String>(getActivity(), myCapitalReycList,
                R.layout.item_discovery_channel) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myCapitalReycAdapter);
        this.linearLayoutManager = new CustomLinearLayoutManager(getActivity());
        this.linearLayoutManager.setScrollEnabled(false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setNestedScrollingEnabled(false);

    }

    private void getData() {
        myCapitalReycList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            myCapitalReycList.add("" + i);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getActivity().getApplicationContext(), this.getClass().getCanonicalName());
    }


    @Override
    public void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getActivity().getApplicationContext(), this.getClass().getCanonicalName());
    }
}
