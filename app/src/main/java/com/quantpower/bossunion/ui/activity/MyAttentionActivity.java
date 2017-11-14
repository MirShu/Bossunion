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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.andview.refreshview.XRefreshView;
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
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * 我的关注
 */

public class MyAttentionActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<DiscoveryList.ListBean> myAttentionReycList = new ArrayList<>();
    private RecyclerAdapter<DiscoveryList.ListBean> myAttentionReycAdapter;

    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.tv_null_bg)
    TextView tvNullBg;

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_attention;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("我的关注");
        this.tvNullBg.setText("大人，精彩要从关注他人开始哦~");
        this.tvNullBg.setVisibility(View.GONE);
        this.xrefreshview.setVisibility(View.VISIBLE);
        this.bindRecycleView();
        this.xRefreshView();
    }

    private void bindRecycleView() {
        this.getData();
        this.myAttentionReycAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(MyAttentionActivity.this, myAttentionReycList,
                R.layout.item_my_attentin) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {
                helper.setImageUrl(R.id.iv_head, item.getSmallpic());
                helper.setText(R.id.tv_name, item.getFamilyName());
                helper.setText(R.id.tv_instru, item.getMyname());
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(MyAttentionActivity.this, 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myAttentionReycAdapter);
        myAttentionReycAdapter.setOnItemClickListener((parent, position) -> {
            String allnum = String.valueOf(myAttentionReycList.get(position).getAllnum());
            String roomid = String.valueOf(myAttentionReycList.get(position).getRoomid());
            Bundle bundle = new Bundle();
            bundle.putString(OthersInformationActivity.SMALLPIC_URL, myAttentionReycList.get(position).getSmallpic());
            bundle.putString(OthersInformationActivity.BIGPIC_URL, myAttentionReycList.get(position).getBigpic());
            bundle.putString(OthersInformationActivity.ALLNUM, allnum);
            bundle.putString(OthersInformationActivity.ROOMID, roomid);
            UIHelper.startActivity(MyAttentionActivity.this, OthersInformationActivity.class, bundle);
        });
    }

    private void getData() {
        RequestParams params = new RequestParams(URLS.ATTENTION_LIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<DiscoveryList.ListBean> list;
                DiscoveryList model = JSON.parseObject(message.getData(), DiscoveryList.class);
                model.getList();
                list = model.getList();
                myAttentionReycList.addAll(list);
                myAttentionReycAdapter.notifyDataSetChanged();
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
