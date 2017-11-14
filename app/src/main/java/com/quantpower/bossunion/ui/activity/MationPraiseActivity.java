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

import com.alibaba.fastjson.JSON;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.DiscoveryList;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.UIHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

public class MationPraiseActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<DiscoveryList.ListBean> myOrderReycList = new ArrayList<>();
    private RecyclerAdapter<DiscoveryList.ListBean> myOrderReycAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_mation_praise;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("赞过我");
        this.bindRecycleView();
    }

    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(MationPraiseActivity.this, myOrderReycList,
                R.layout.item_mation_praise) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {
                ImageView ivHead = helper.getView(R.id.iv_head);
                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvTime = helper.getView(R.id.tv_time);
                TextView tvDuty = helper.getView(R.id.tv_duty);
                TextView tvDate = helper.getView(R.id.tv_date);
                TextView tvReviewt = helper.getView(R.id.tv_review_tit);
                helper.setText(R.id.tv_duty, item.getMyname());
                helper.setText(R.id.tv_name, item.getFamilyName());
                helper.setImageUrl(R.id.iv_head, item.getSmallpic());
                if (position == 0) {
                    tvName.setText("宋总监");
                    tvTime.setText("给了你一个赞");
                    tvDuty.setText("上海永诚网络科技有限公司总经理");
                    tvDate.setVisibility(View.VISIBLE);
                    tvReviewt.setText("中国实体经济理念崛起");
                } else if (position == 1) {
                    tvName.setText("赵庆");
                    tvTime.setText("给了你一个赞");
                    tvDuty.setText("网上平台技术部cto");
                    tvReviewt.setText("大时代面前你做了这几件事了吗");
                } else if (position == 2) {
                    tvName.setText("董泽明");
                    tvTime.setText("给了你一个赞");
                    tvDuty.setText("点对点(上海)商务总监");
                    tvReviewt.setText("上海今年的金融行情你如何看待");
                } else if (position == 3) {
                    tvName.setText("孙明");
                    tvTime.setText("给了你一个赞");
                    tvDuty.setText("海科软件(上海)有限公司总经理");
                    tvDate.setVisibility(View.VISIBLE);
                    tvReviewt.setText("金融魔都明年的改变你知道吗");
                } else if (position == 4) {
                    tvName.setText("邓琪");
                    tvTime.setText("给了你一个赞");
                    tvDuty.setText("上海敏俊集团资深HR总监");
                    tvReviewt.setText("失业，论你有多大的资本");
                }

            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(MationPraiseActivity.this, 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        this.myOrderReycAdapter.setOnItemClickListener((parent, position) -> {
            String allnum = String.valueOf(myOrderReycList.get(position).getAllnum());
            String roomid = String.valueOf(myOrderReycList.get(position).getRoomid());
            Bundle bundle = new Bundle();
            bundle.putString(OthersInformationActivity.SMALLPIC_URL, myOrderReycList.get(position).getSmallpic());
            bundle.putString(OthersInformationActivity.BIGPIC_URL, myOrderReycList.get(position).getBigpic());
            bundle.putString(OthersInformationActivity.MYNAME, myOrderReycList.get(position).getMyname());
            bundle.putString(OthersInformationActivity.ALLNUM, allnum);
            bundle.putString(OthersInformationActivity.ROOMID, roomid);
            UIHelper.startActivity(MationPraiseActivity.this, OthersInformationActivity.class, bundle);
        });

    }

    private void getData() {
        RequestParams params = new RequestParams(URLS.MATION_PRAISE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<DiscoveryList.ListBean> list;
                DiscoveryList model = JSON.parseObject(message.getData(), DiscoveryList.class);
                model.getList();
                list = model.getList();
                myOrderReycList.addAll(list);
                myOrderReycAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

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
