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
 * Created by ShuLin on 2017/7/17.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class EquityFragment extends BaseFragment {
    public Activity mContext;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CustomLinearLayoutManager linearLayoutManager;
    private RecyclerAdapter myCapitalReycAdapter;
    private List<String> myCapitalReycList;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_equity;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.bindRecycleView();
    }

    private void bindRecycleView() {
        this.getData();
        this.myCapitalReycAdapter = new RecyclerAdapter<String>(getActivity(), myCapitalReycList,
                R.layout.item_my_capital) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvStatus = helper.getView(R.id.tv_status);
                TextView tvInterestedIn = helper.getView(R.id.tv_interested_in);
                String string = MessageFormat.format("<font color=\"#6b9bcd\">{0}</font>人感兴趣", "5100");
                tvInterestedIn.setText(Html.fromHtml(string));
                if (position == 0) {
                    tvStatus.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_333333));
                    tvStatus.setText("融资完成");
                } else {
                    tvStatus.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_6b9bcd));
                    tvStatus.setText("融资中");
                    tvStatus.getBackground().setAlpha(75);
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myCapitalReycAdapter);
        this.linearLayoutManager = new CustomLinearLayoutManager(mContext);
        this.linearLayoutManager.setScrollEnabled(false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setNestedScrollingEnabled(false);

    }

    private void getData() {
        myCapitalReycList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
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
