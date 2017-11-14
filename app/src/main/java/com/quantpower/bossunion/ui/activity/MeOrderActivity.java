/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
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
 * Created by ShuLin on 2017/5/6.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MeOrderActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private RecyclerAdapter myOrderReycAdapter;
    private List<String> myOrderReycList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.tv_null_bg)
    TextView tvNullBg;

    @Override
    public int getContentViewId() {
        return R.layout.activity_me_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("我的订单");
        this.bindRecycleView();
        this.xRefreshView();
        this.tvNullBg.setText("大人，您没有相订单哦~");
        this.tvNullBg.setVisibility(View.GONE);
        this.xrefreshview.setVisibility(View.VISIBLE);
    }

    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<String>(MeOrderActivity.this, myOrderReycList,
                R.layout.item_me_order) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvState = helper.getView(R.id.tv_state);
                ImageView ivHead=helper.getView(R.id.iv_head);
                LinearLayout llDelate = helper.getView(R.id.bt_item_delate);
                RelativeLayout itemOrder = helper.getView(R.id.item_order);
                if (position == 0) {
                    tvState.setText("已完成");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_333333));
                    ivHead.setImageResource(R.mipmap.icon_dis00);
                } else if (position == 1) {
                    tvState.setText("待付款");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
                    tvState.getBackground().setAlpha(75);
                    ivHead.setImageResource(R.mipmap.icon_dis01);
                } else if (position == 2) {
                    tvState.setText("可使用");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
                    tvState.getBackground().setAlpha(75);
                    ivHead.setImageResource(R.mipmap.icon_dis02);
                } else if (position == 3) {
                    tvState.setText("退款中");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_333333));
                    ivHead.setImageResource(R.mipmap.icon_dis03);
                } else if (position == 4) {
                    tvState.setText("已关闭");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_333333));
                    ivHead.setImageResource(R.mipmap.icon_dis04);
                } else {
                    tvState.setText("已完成");
                    tvState.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_333333));
                    ivHead.setImageResource(R.mipmap.icon_dis01);
                }

                llDelate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.toastMessage(MeOrderActivity.this, "哈哈。。。皮皮虾你没删掉我");
                    }
                });

                itemOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        int tag = Integer.valueOf(myOrderReycList.get(position));
                        bundle.putInt(OrderDetailsActivity.ORDER_TAG, tag);
                        UIHelper.startActivity(MeOrderActivity.this, OrderDetailsActivity.class, bundle);
                    }
                });
            }

        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(MeOrderActivity.this, 1,
                LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
    }

    private void getData() {
        myOrderReycList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            myOrderReycList.add("" + i);
        }

    }

    /**
     * 刷新机制
     */
    private void xRefreshView() {
        this.xrefreshview.setAutoRefresh(true);
        this.xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> xrefreshview.stopRefresh(), 1000);
            }

            @Override
            public void onLoadMore(boolean isSlience) {

            }
        });
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
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
