/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.base.BaseApplication;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.ActivityManagerUtils;
import com.quantpower.bossunion.utils.PhoneLegal;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.extend.WelcomeVideoView;
import com.rey.material.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/3.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_code)
    EditText etLoginCode;
    @BindView(R.id.tv_login_ident)
    TextView tvLoginIdent;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.tv_main_title)
    android.widget.TextView tvMainTitle;
    TimeCount time;
    @BindView(R.id.videoview)
    WelcomeVideoView videoview;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        ActivityManagerUtils.getInstance().addActivity(this);
        tvMainTitle.setText(getResources().getString(R.string.tv_login));
        time = new TimeCount(60000, 1000);
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.guide_1));
        videoview.start();
        videoview.setOnCompletionListener(mediaPlayer -> videoview.start());
    }

    @OnClick({R.id.image_back, R.id.butt_login, R.id.et_login_phone, R.id.et_login_code, R.id.tv_login_ident, R.id.tv_cannot_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.butt_login:
                loginView();
                UIHelper.startActivity(LoginActivity.this, MainActivity.class);
                break;
            case R.id.et_login_phone:
                break;
            case R.id.et_login_code:
                break;
            case R.id.tv_cannot_login:
                UIHelper.startActivity(LoginActivity.this, CannotLoginActivity.class);
                break;
            case R.id.tv_login_ident:
                identView();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void identView() {
        etLoginCode.setNextFocusDownId(R.id.et_login_code);
        String mPhone = etLoginPhone.getText().toString().trim();
        boolean result = PhoneLegal.isPhone(mPhone);
        if (TextUtils.isEmpty(mPhone)) {
            UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_input_phone));
            return;
        }
        if (result == false) {
            UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_input_phone_erro));
            return;
        } else {
            etLoginCode.requestFocus();
            etLoginCode.getImeOptions();
            RequestParams params = new RequestParams(URLS.USER_SENDCODE);
            params.addBodyParameter("phone", mPhone);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    time.start();
                    MessageResult message = MessageResult.parse(result);
                    if (message.getCode() == 0) {
                        UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_code_success));
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_get_code_error));
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
     * 登录
     */
    private void loginView() {
        String mPhone = etLoginPhone.getText().toString().trim();
        String mCode = etLoginCode.getText().toString().trim();
        if (mPhone.isEmpty() || mCode.isEmpty()) {
            UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_correct_phone_number));
        } else {
            RequestParams params = new RequestParams(URLS.USER_USER_LOGIN);
            params.addBodyParameter("phone", mPhone);
            params.addBodyParameter("code", mCode);
            params.addBodyParameter("regid", "666");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    MessageResult message = MessageResult.parse(result);
                    if (message.getCode() == 0) {
                        UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_login_success));
                        UIHelper.startActivity(LoginActivity.this, MainActivity.class);


                    } else {
                        UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_code_error));
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    UIHelper.toastMessage(LoginActivity.this, getResources().getString(R.string.tv_code_error));
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
            tvLoginIdent.setClickable(false);
            tvLoginIdent.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvLoginIdent.setText("重发");
            tvLoginIdent.setClickable(true);
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
