/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/6/21.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MationNewsActivity extends BaseActivity {

    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerAdapter myOrderReycAdapter;
    private List<String> myOrderReycList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mation_news;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("系统消息");
        this.bindRecycleView();
    }


    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<String>(MationNewsActivity.this, myOrderReycList,
                R.layout.item_mation_news) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                ImageView ivHead = helper.getView(R.id.iv_head);
                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvTime = helper.getView(R.id.tv_time);
                TextView tvDuty = helper.getView(R.id.tv_duty);
                if (position == 0) {
                    ivHead.setImageResource(R.mipmap.icon_mation01);
                    tvName.setText("欢迎老板加入波士界APP，");
                    tvTime.setText("2017-06-20");
                    tvDuty.setText("上海永诚网络科技有限公司总经理");
                } else if (position == 1) {
                    ivHead.setImageResource(R.mipmap.icon_mation02);
                    tvName.setText("欢迎老板加入波士界APP，");
                    tvTime.setText("2017-02-10");
                    tvDuty.setText("网上平台技术部cto");
                } else if (position == 2) {
                    ivHead.setImageResource(R.mipmap.icon_mation013);
                    tvName.setText("欢迎老板加入波士界APP，");
                    tvTime.setText("2017-05-10");
                    tvDuty.setText("点对点(上海)商务总监");
                } else if (position == 3) {
                    ivHead.setImageResource(R.mipmap.icon_mation04);
                    tvName.setText("欢迎老板加入波士界APP，");
                    tvTime.setText("2017-04-17");
                    tvDuty.setText("海科软件(上海)有限公司总经理");
                } else if (position == 4) {
                    ivHead.setImageResource(R.mipmap.icon_mation05);
                    tvName.setText("欢迎老板加入波士界APP，");
                    tvTime.setText("2017-05-02");
                    tvDuty.setText("上海敏俊集团资深HR总监");
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(MationNewsActivity.this, 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        this.myOrderReycAdapter.setOnItemClickListener((parent, position)
                -> UIHelper.startActivity(MationNewsActivity.this, OthersInformationActivity.class));

    }

    private void getData() {
        myOrderReycList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            myOrderReycList.add("" + i);
        }

    }


    @OnClick(R.id.iv_back_icon)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_icon:
                finish();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getApplicationContext(), this.getClass().getCanonicalName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getApplicationContext(), this.getClass().getCanonicalName());
    }
}
