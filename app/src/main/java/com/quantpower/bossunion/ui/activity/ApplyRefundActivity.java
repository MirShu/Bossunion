/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.scollview.ReboundScrollView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ApplyRefundActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.ll_successful)
    LinearLayout llSuccessful;
    @BindView(R.id.scrollView)
    ReboundScrollView scrollView;
    @BindView(R.id.tv_come_back)
    TextView tvComeBack;

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_refund;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("退款申请");

    }

    @OnClick({R.id.image_back, R.id.tv_confirm, R.id.tv_come_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_confirm:
                llSuccessful.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                tvComeBack.setText("返回至我的订单列表");
                break;
            case R.id.tv_come_back:
                UIHelper.startActivity(ApplyRefundActivity.this, MeOrderActivity.class);
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
