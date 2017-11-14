/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.PhoneLegal;
import com.quantpower.bossunion.utils.UIHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/2.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_ident)
    TextView tvIdent;
    @BindView(R.id.butt_register)
    Button buttRegister;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    TimeCount time;

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("注册");
        time = new RegisterActivity.TimeCount(60000, 1000);
    }

    @OnClick({R.id.image_back, R.id.butt_register, R.id.tv_ident, R.id.tv_user_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.butt_register:
                UIHelper.startActivity(RegisterActivity.this, UserIndustryActivity.class);
                registerNext();
                break;
            case R.id.tv_ident:
                identView();
                break;
            case R.id.tv_user_agreement:
                UIHelper.startActivity(RegisterActivity.this, UsersAgreementActivity.class);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void identView() {
        etCode.setNextFocusDownId(R.id.et_code);
        String mPhone = etPhone.getText().toString().trim();
        boolean result = PhoneLegal.isPhone(mPhone);
        if (TextUtils.isEmpty(mPhone)) {
            UIHelper.toastMessage(RegisterActivity.this, getResources().getString(R.string.tv_input_phone));
            return;
        }
        if (result == false) {
            UIHelper.toastMessage(RegisterActivity.this, getResources().getString(R.string.tv_input_phone_erro));
            return;
        } else {
            etCode.requestFocus();
            etCode.getImeOptions();
            RequestParams params = new RequestParams(URLS.USER_SENDCODE);
            params.addBodyParameter("phone", mPhone);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    time.start();
                    MessageResult message = MessageResult.parse(result);
                    if (message.getCode() == 0) {
                        UIHelper.toastMessage(RegisterActivity.this, getResources().getString(R.string.tv_code_success));
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

    }

    /**
     * 检测CODE点击注册下一步按钮
     */
    private void registerNext() {
        String mPhone = etPhone.getText().toString().trim();
        String mCode = etCode.getText().toString().trim();
        if (mPhone.isEmpty() || mCode.isEmpty()) {
            UIHelper.toastMessage(RegisterActivity.this, getResources().getString(R.string.tv_correct_phone_number));
        } else {
            RequestParams params = new RequestParams(URLS.USER_CHECK_CODE);
            params.addBodyParameter("phone", mPhone);
            params.addBodyParameter("code", mCode);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    MessageResult message = MessageResult.parse(result);
                    if (message.getCode() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("CODE", mCode);
                        bundle.putString("PHONE", mPhone);
                        UIHelper.startActivity(RegisterActivity.this, UserIndustryActivity.class, bundle);
                    } else {
                        UIHelper.toastMessage(RegisterActivity.this, "验证码错误或已过期");
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIHelper.toastMessage(RegisterActivity.this, "验证码错误或已过期");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvIdent.setClickable(false);
            tvIdent.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvIdent.setText("重发");
            tvIdent.setClickable(true);
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
