/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

import android.app.Activity;
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
import com.quantpower.bossunion.model.DiscoveryList;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.ui.activity.MationClerkActivity;
import com.quantpower.bossunion.ui.activity.MationNewsActivity;
import com.quantpower.bossunion.ui.activity.MationPraiseActivity;
import com.quantpower.bossunion.ui.activity.MationReviewActivity;
import com.quantpower.bossunion.ui.activity.OthersInformationActivity;
import com.quantpower.bossunion.utils.Constants;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.CustomDialog;
import com.quantpower.bossunion.widget.dialog.LodingDialog;
import com.quantpower.bossunion.widget.extend.CustomLinearLayoutManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class InformationFragment extends BaseFragment {
    public Activity mContext;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    LodingDialog lodingDialog;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<DiscoveryList.ListBean> myOrderReycList = new ArrayList<>();
    private RecyclerAdapter<DiscoveryList.ListBean> myOrderReycAdapter;

    private CustomLinearLayoutManager linearLayoutManager;

    public static InformationFragment newInstance(String s) {
        InformationFragment homeFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_information;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("消息");
        this.bindRecycleView();
        lodingDialog = new LodingDialog(getActivity(), "加载中...");
        lodingDialog.show();
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
    }

    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(getActivity(), myOrderReycList,
                R.layout.item_me_visit) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {
                ImageView ivHead = helper.getView(R.id.iv_head);
                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvDuty = helper.getView(R.id.tv_duty);
                helper.setText(R.id.tv_time, item.getStarlevel() + "分钟前");
                helper.setText(R.id.tv_duty, item.getMyname());
                helper.setText(R.id.tv_name, item.getFamilyName());
                helper.setImageUrl(R.id.iv_head, item.getSmallpic());
                if (position == 0) {
                    tvName.setText("宋总监");
                    tvDuty.setText("上海永诚网络科技有限公司总经理");
                } else if (position == 1) {
                    tvName.setText("赵庆");
                    tvDuty.setText("网上平台技术部cto");
                } else if (position == 2) {
                    tvName.setText("董泽明");
                    tvDuty.setText("点对点(上海)商务总监");
                } else if (position == 3) {
                    tvName.setText("孙明");
                    tvDuty.setText("海科软件(上海)有限公司总经理");
                } else if (position == 4) {
                    ivHead.setImageResource(R.mipmap.icon_mation05);
                    tvName.setText("邓琪");
                    tvDuty.setText("上海敏俊集团资深HR总监");
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
        this.linearLayoutManager = new CustomLinearLayoutManager(mContext);
        this.linearLayoutManager.setScrollEnabled(false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
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
            UIHelper.startActivity(getActivity(), OthersInformationActivity.class, bundle);
        });
    }

    private void getData() {
        RequestParams params = new RequestParams(URLS.INFORMATION_LIST);
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
                lodingDialog.dismiss();
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

    @OnClick({R.id.tv_mation_news, R.id.tv_mation_clerk, R.id.tv_mation_review, R.id.tv_mation_praise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mation_news:
                UIHelper.startActivity(getActivity(), MationNewsActivity.class);
                break;
            case R.id.tv_mation_clerk:
                UIHelper.startActivity(getActivity(), MationClerkActivity.class);
                break;
            case R.id.tv_mation_review:
                UIHelper.startActivity(getActivity(), MationReviewActivity.class);
                break;
            case R.id.tv_mation_praise:
                UIHelper.startActivity(getActivity(), MationPraiseActivity.class);
                break;
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
