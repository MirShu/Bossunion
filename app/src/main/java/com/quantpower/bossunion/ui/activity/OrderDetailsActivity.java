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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.LodingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class OrderDetailsActivity extends BaseActivity {
    public static final String ORDER_TAG = "news_id_tag";
    @BindView(R.id.tv_state_of_payment)
    TextView tvStateOfPayment;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_invoice)
    com.rey.material.widget.TextView tvInvoice;
    @BindView(R.id.ll_closed_order)
    LinearLayout llClosedOrder;
    @BindView(R.id.ll_unpaid)
    LinearLayout llUnpaid;
    @BindView(R.id.tv_countdown_view)
    CountdownView tvCountdownView;
    @BindView(R.id.rl_pay)
    RelativeLayout rlPay;
    @BindView(R.id.tv_pay)
    com.rey.material.widget.TextView tvPay;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_refund_num)
    TextView tvRefundNum;
    @BindView(R.id.tv_refund_reason)
    TextView tvRefundReason;
    @BindView(R.id.tv_refund_content)
    TextView tvRefundContent;
    @BindView(R.id.ll_unpaid_down)
    LinearLayout llUnpaidDown;
    private int orderTag;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_validation)
    TextView tvValidation;
    @BindView(R.id.tv_refund_success)
    TextView TvRefundSuccess;
    LodingDialog lodingDialog;
    @Override
    public int getContentViewId() {
        return R.layout.activity_order_details;

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.lodingDialog = new LodingDialog(OrderDetailsActivity.this, "加载中...");
        this.lodingDialog.show();
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
        tvCountdownView.setTag("test2");
        long time2 = (long) 30 * 60 * 1000;
        tvCountdownView.start(time2);
        this.orderTag = getIntent().getIntExtra(ORDER_TAG, 0);
        tvMainTitle.setText("订单详情");
        if (orderTag == 0) {
            tvStateOfPayment.setText("已完成");
            tvDescribe.setVisibility(View.GONE);
        } else if (orderTag == 1) {
            tvStateOfPayment.setText("待支付");
            tvStateOfPayment.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
            llUnpaid.setVisibility(View.VISIBLE);
            tvDescribe.setVisibility(View.GONE);
            rlPay.setVisibility(View.VISIBLE);
            tvInvoice.setVisibility(View.GONE);
            tvPay.setVisibility(View.VISIBLE);
            tvPay.setOnClickListener(v -> UIHelper.toastMessage(OrderDetailsActivity.this, "待支付"));

        } else if (orderTag == 2) {
            tvStateOfPayment.setText("可使用");
            tvStateOfPayment.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
            llClosedOrder.setVisibility(View.VISIBLE);
            tvNote.setVisibility(View.VISIBLE);
            tvInvoice.setVisibility(View.GONE);
            tvDescribe.setText("活动当天APP扫码可验证消费，期间您的到来。");
            tvPay.setVisibility(View.VISIBLE);
            tvPay.setText("提交退款申请");
            tvPay.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            tvPay.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.color_bababa));
            tvPay.setOnClickListener(v -> UIHelper.startActivity(OrderDetailsActivity.this, ApplyRefundActivity.class));
        } else if (orderTag == 3) {
            tvStateOfPayment.setText("退款中");
            tvStateOfPayment.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.color_6b9bcd));
            tvDescribe.setText(" 申请通过后，钱款将在15个工作日内原路返回至您的账户上。请您耐心等待");
            tvRefund.setVisibility(View.VISIBLE);
            tvRefundNum.setVisibility(View.VISIBLE);
            tvRefundReason.setVisibility(View.VISIBLE);
            tvRefundContent.setVisibility(View.VISIBLE);
            llUnpaidDown.setVisibility(View.VISIBLE);
            tvValidation.setText("申请退款：2017-05-10 14:22:10");
            tvInvoice.setVisibility(View.GONE);
        } else if (orderTag == 4) {
            tvStateOfPayment.setText("已关闭");
            tvDescribe.setText("订单已退款，请注意查收");
            tvRefund.setVisibility(View.VISIBLE);
            tvRefundNum.setVisibility(View.VISIBLE);
            tvRefundReason.setVisibility(View.VISIBLE);
            tvRefundContent.setVisibility(View.VISIBLE);
            llUnpaidDown.setVisibility(View.VISIBLE);
            tvValidation.setText("申请退款：2017-05-10 14:22:10");
            TvRefundSuccess.setVisibility(View.VISIBLE);
            tvInvoice.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.image_back, R.id.tv_invoice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_invoice:
                UIHelper.startActivity(OrderDetailsActivity.this, InvoiceActivity.class);
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
