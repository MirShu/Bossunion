/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.FlowLayoutManager;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.extend.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * 修改关注行业
 */

public class DTModifyIndustry extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private RecyclerAdapter industryReycAdapter;
    private List<String> industryReycList;
    private RecyclerAdapter industryOnReycAdapter;
    private List<String> industryOnReycList = new ArrayList<>();
    private final int TEXTCOLOR = 0x5f666666;
    private final int TEXTADDCOLOR = 0xff666666;

    @BindView(R.id.recy_industry)
    RecyclerView recyIndustry;
    @BindView(R.id.recy_industry_on)
    RecyclerView recyIndustryOn;

    @Override
    public int getContentViewId() {
        return R.layout.activity_dt_industy;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("行业修改");
        bindRecycleView();

    }

    @OnClick({R.id.image_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_save:
                ToastUtil.showShareToast(DTModifyIndustry.this, R.mipmap.icon_success, "保存成功");
                break;
        }
    }


    private void bindRecycleView() {
        this.getData();
        this.industryReycAdapter = new RecyclerAdapter<String>(DTModifyIndustry.this, industryReycList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                helper.setText(R.id.tv_title, industryReycList.get(position));
                helper.setText(R.id.tv_title_on, industryReycList.get(position));
                mTitleOn.getBackground().setAlpha(102);
                mTitleOn.setTextColor(TEXTCOLOR);
                mTitle.setTextColor(TEXTADDCOLOR);
                if (industryOnReycList.size() == 0) {
                    mTitle.setVisibility(View.VISIBLE);
                    mTitleOn.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < industryOnReycList.size(); i++) {
                        if (mTitle.getText() == (industryOnReycList.get(i))) {
                            mTitle.setVisibility(View.GONE);
                            mTitleOn.setVisibility(View.VISIBLE);
                            break;
                        }
                        mTitle.setVisibility(View.VISIBLE);
                        mTitleOn.setVisibility(View.GONE);
                    }
                }

                mTitleOn.setClickable(false);
                mTitle.setOnClickListener(view -> {
                    if (industryOnReycList.size() >= 5) {
                        UIHelper.toastMessage(DTModifyIndustry.this, "请选择3-5个行业");
                    } else {
                        mTitle.setVisibility(View.GONE);
                        mTitleOn.setVisibility(View.VISIBLE);
                        industryOnReycList.add(mTitle.getText().toString());
                        industryOnReycAdapter.notifyDataSetChanged();
                    }
                });

            }
        };


        this.industryOnReycAdapter = new RecyclerAdapter<String>(DTModifyIndustry.this, industryOnReycList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                TextView mTitleUp = helper.getView(R.id.tv_title_up);
                mTitleUp.setVisibility(View.VISIBLE);
                mTitleOn.setVisibility(View.GONE);
                mTitle.setVisibility(View.GONE);

                mTitleUp.setOnClickListener(view -> {
                    industryOnReycList.remove(position);
                    industryOnReycAdapter.notifyDataSetChanged();
                    industryReycAdapter.notifyDataSetChanged();
                });

                helper.setText(R.id.tv_title_up, industryOnReycList.get(position));

            }
        };

        this.recyIndustry.setHasFixedSize(true);
        this.recyIndustry.setLayoutManager(new FlowLayoutManager());
        this.recyIndustry.setAdapter(this.industryReycAdapter);

        this.recyIndustryOn.setHasFixedSize(true);
        this.recyIndustryOn.setLayoutManager(new FlowLayoutManager());
        this.recyIndustryOn.setAdapter(this.industryOnReycAdapter);
    }

    private void getData() {
        industryReycList = new ArrayList<>();
        industryReycList.add("金融行业");
        industryReycList.add("海关一号");
        industryReycList.add("天使投资");
        industryReycList.add("007");
        industryReycList.add("你是我的");
        industryReycList.add("我");
        industryReycList.add("计算机");
        industryReycList.add("转圈圈");
        industryReycList.add("波士界");
        industryReycList.add("boss界");
        industryReycList.add("我的歌神");
        industryReycList.add("你的优乐美好喝");
        industryReycList.add("喝了还想喝");

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
