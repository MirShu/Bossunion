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
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;

import java.text.MessageFormat;
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

public class MeCapitalActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    private RecyclerAdapter myCapitalReycAdapter;
    private List<String> myCapitalReycList;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_null_bg)
    TextView tvNullBg;

    @Override
    public int getContentViewId() {
        return R.layout.activity_me_capital;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("我的创投");
        this.bindRecycleView();
        this.xRefreshView();
        this.tvNullBg.setText("大人，您还没有感兴趣的项目哦~");
        this.tvNullBg.setVisibility(View.GONE);
        this.xrefreshview.setVisibility(View.VISIBLE);
    }

    private void bindRecycleView() {
        this.getData();
        this.myCapitalReycAdapter = new RecyclerAdapter<String>(MeCapitalActivity.this, myCapitalReycList,
                R.layout.item_my_capital) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvStatus = helper.getView(R.id.tv_status);
                TextView tvInterestedIn = helper.getView(R.id.tv_interested_in);
                String string = MessageFormat.format("<font color=\"#6b9bcd\">{0}</font>人感兴趣", "5100");
                tvInterestedIn.setText(Html.fromHtml(string));
                if (position == 0) {
                    tvStatus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_333333));
                    tvStatus.setText("融资完成");
                } else {
                    tvStatus.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
                    tvStatus.setText("融资中");
                    tvStatus.getBackground().setAlpha(75);
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(MeCapitalActivity.this, 1,
                LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myCapitalReycAdapter);

    }

    private void getData() {
        myCapitalReycList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            myCapitalReycList.add("" + i);
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
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
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
