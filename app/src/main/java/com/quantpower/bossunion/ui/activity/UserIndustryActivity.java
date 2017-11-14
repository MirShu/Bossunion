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

import com.alibaba.fastjson.JSON;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.FlowLayoutManager;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.IndustryModel;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.UIHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by likai on 2017/5/5.
 * email: codingkai@163.com
 */

public class UserIndustryActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private RecyclerAdapter<IndustryModel> industryReycAdapter;
    private List<IndustryModel> industryReycList = new ArrayList<>();


    private RecyclerAdapter<IndustryModel> industryOnReycAdapter;
    private List<IndustryModel> industryOnReycList = new ArrayList<>();
    private final int TEXTCOLOR = 0x5fffffff;

    @BindView(R.id.recy_industry)
    RecyclerView recyIndustry;
    @BindView(R.id.recy_industry_on)
    RecyclerView recyIndustryOn;
    private String mCode;
    private String mPhone;

    @Override
    public int getContentViewId() {
        return R.layout.activity_user_industry;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.bindRecycleView();
        tvMainTitle.setVisibility(View.GONE);
        mCode = this.getIntent().getStringExtra("CODE");
        mPhone = this.getIntent().getStringExtra("PHONE");

    }

    private void bindRecycleView() {
        this.getData();
        this.industryReycAdapter = new RecyclerAdapter<IndustryModel>(UserIndustryActivity.this, industryReycList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, IndustryModel item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                helper.setText(R.id.tv_title, item.getTrade_name());
                helper.setText(R.id.tv_title_on, item.getTrade_name());
                mTitleOn.getBackground().setAlpha(102);
                mTitleOn.setTextColor(TEXTCOLOR);
                if (industryOnReycList.size() == 0) {
                    mTitle.setVisibility(View.VISIBLE);
                    mTitleOn.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < industryOnReycList.size(); i++) {
                        if (mTitle.getText() == (industryOnReycList.get(i).getTrade_name())) {
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
                        UIHelper.toastMessage(UserIndustryActivity.this, "请选择3-5个行业");
                    } else {
                        mTitle.setVisibility(View.GONE);
                        mTitleOn.setVisibility(View.VISIBLE);
                        industryOnReycList.add(industryReycList.get(position));
                        industryOnReycAdapter.notifyDataSetChanged();
                    }
                });

            }
        };


        this.industryOnReycAdapter = new RecyclerAdapter<IndustryModel>(UserIndustryActivity.this, industryOnReycList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, IndustryModel item, int position) {
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

                helper.setText(R.id.tv_title_up, industryOnReycList.get(position).getTrade_name());

            }
        };

        this.recyIndustry.setHasFixedSize(true);
        this.recyIndustry.setLayoutManager(new FlowLayoutManager());
        this.recyIndustry.setAdapter(this.industryReycAdapter);

        this.recyIndustryOn.setHasFixedSize(true);
        this.recyIndustryOn.setLayoutManager(new FlowLayoutManager());
        this.recyIndustryOn.setAdapter(this.industryOnReycAdapter);
    }


    /**
     * 获取行业标签
     */
    private void getData() {
        RequestParams parames = new RequestParams(URLS.USER_GETTRADE);
        x.http().get(parames, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    MessageResult message = MessageResult.parse(result);
                    List<IndustryModel> industryList = JSON.parseArray(message.getData(),
                            IndustryModel.class);
                    industryReycList.addAll(industryList);
                    industryReycAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
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

    @OnClick({R.id.butt_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.butt_next_step:
                UIHelper.startActivity(UserIndustryActivity.this, NameRealActivity.class);
                if (industryOnReycList.size() < 3) {
                    UIHelper.toastMessage(UserIndustryActivity.this, "请选择3-5个行业");
                } else {
                    StringBuffer trade_id = new StringBuffer();
                    for (int i = 0; i < industryOnReycList.size(); i++) {
                        if (i == 0) {
                            trade_id.append(industryOnReycList.get(i).getTrade_id());
                        } else {
                            trade_id.append(",");
                            trade_id.append(industryOnReycList.get(i).getTrade_id());
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("INDUSTRY", trade_id.toString());
                    bundle.putString("CODE", mCode);
                    bundle.putString("PHONE", mPhone);
                    UIHelper.startActivity(UserIndustryActivity.this, NameRealActivity.class, bundle);
                }
                break;
        }
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
