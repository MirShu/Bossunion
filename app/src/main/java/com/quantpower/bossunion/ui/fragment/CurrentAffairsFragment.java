package com.quantpower.bossunion.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.model.NewsModel;
import com.quantpower.bossunion.model.WeixinNewsModel;
import com.quantpower.bossunion.ui.activity.TopicDetailsActivity;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.LodingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/7/30.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class CurrentAffairsFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LodingDialog lodingDialog;
    private List<WeixinNewsModel.ListBean> myOrderReycList = new ArrayList<>();
    private RecyclerAdapter<WeixinNewsModel.ListBean> myOrderReycAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_current_affairs;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.bindRecycleView();

    }

    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<WeixinNewsModel.ListBean>(getActivity(), myOrderReycList,
                R.layout.item_topics_focuse) {
            @Override
            public void convert(RecyclerViewHolder helper, WeixinNewsModel.ListBean item, int position) {
                TextView tvNew = helper.getView(R.id.tv_new);
                TextView topocsTitle = helper.getView(R.id.topocs_title);
                TextView tvShareZan = helper.getView(R.id.tv_share_zan);
                helper.setImageUrl(R.id.iv_bg, item.getFirstImg());
                helper.setText(R.id.topocs_title, item.getTitle());
                ImageView iv_bg = helper.getView(R.id.iv_bg);
                if (position == 0) {
                    iv_bg.setImageResource(R.mipmap.bg_top01);
                    topocsTitle.setText("重资产资金链上的石油问题，研讨大会");
                    tvShareZan.setText("254点赞，12001分享，635讨论");
                } else if (position == 1) {
                    iv_bg.setImageResource(R.mipmap.bg_top02);
                    topocsTitle.setText("亚峰会关于互联网金融整顿，乱、杂、不合格现象");
                    tvShareZan.setText("901点赞，3542分享，654讨论");
                } else if (position == 2) {
                    iv_bg.setImageResource(R.mipmap.bg_top03);
                    topocsTitle.setText("产业经济扶持问题，今后长远目标该放在哪个环节，重点关注");
                    tvNew.setVisibility(View.GONE);
                    tvShareZan.setText("54221点赞，6555分享，6350讨论");
                } else if (position == 3) {
                    iv_bg.setImageResource(R.mipmap.bg_top04);
                    topocsTitle.setText("企业规划，规整，规模意义的本质性");
                    tvNew.setVisibility(View.GONE);
                    tvShareZan.setText("3544点赞，2530分享，2542讨论");
                } else if (position == 4) {
                    iv_bg.setImageResource(R.mipmap.bg_top01);
                    tvNew.setVisibility(View.GONE);
                    tvShareZan.setText("1万点赞，3500分享，9000讨论");
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        this.myOrderReycAdapter.setOnItemClickListener((parent, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(TopicDetailsActivity.NEWSURL, myOrderReycList.get(position).getUrl());
            bundle.putString(TopicDetailsActivity.NEWTITLE, myOrderReycList.get(position).getTitle());
            UIHelper.startActivity(getActivity(), TopicDetailsActivity.class, bundle);
        });
    }

    private void getData() {
        RequestParams params = new RequestParams(URLS.WEIXIN_QUERY);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<WeixinNewsModel.ListBean> list;
                WeixinNewsModel model = JSON.parseObject(message.getResult(), WeixinNewsModel.class);
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
