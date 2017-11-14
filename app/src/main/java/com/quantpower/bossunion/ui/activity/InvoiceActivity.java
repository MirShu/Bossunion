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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class InvoiceActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.ll_successful)
    LinearLayout llSuccessful;
    @BindView(R.id.ll_invoic)
    LinearLayout llInvoic;


    @Override
    public int getContentViewId() {
        return R.layout.activity_invoice;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("发票申请");
    }


    @OnClick({R.id.tv_come_back, R.id.butt_submit, R.id.image_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_come_back:
                UIHelper.startActivity(InvoiceActivity.this, MeOrderActivity.class);
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.butt_submit:
                llSuccessful.setVisibility(View.VISIBLE);
                llInvoic.setVisibility(View.GONE);
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
