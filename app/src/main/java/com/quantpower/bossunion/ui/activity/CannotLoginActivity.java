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
import android.widget.ImageView;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.OBAlertDialog;
import com.rey.material.widget.Button;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/3.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class CannotLoginActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_position)
    EditText edPosition;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.ed_resident_city)
    EditText edResidentCity;
    @BindView(R.id.ed_last_login_time)
    EditText edLastLoginTime;
    @BindView(R.id.ed_detailed)
    EditText edDetailed;
    @BindView(R.id.butt_submit)
    Button buttSubmit;

    @Override
    public int getContentViewId() {
        return R.layout.activity_cannot_login;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("无法登陆");
    }

    @OnClick({R.id.image_back, R.id.butt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.butt_submit:
                submit();
                break;
        }
    }

    /**
     * 无法登陆提交相关信息
     */
    private void submit() {
        String mPhone = edPhone.getText().toString().trim();
        String mName = edName.getText().toString().trim();
        String mPosition = edPosition.getText().toString().trim();
        String mCompany = edCompany.getText().toString().trim();
        String mResidentCity = edResidentCity.getText().toString().trim();
        String mLastLoginTime = edLastLoginTime.getText().toString().trim();
        String mDetailed = edDetailed.getText().toString().trim();
        RequestParams params = new RequestParams(URLS.USER_ALTERUSER_PHONE);
        params.addBodyParameter("uphone", mPhone);
        params.addBodyParameter("uname", mName);
        params.addBodyParameter("position", mPosition);
        params.addBodyParameter("company", mCompany);
        params.addBodyParameter("city", mResidentCity);
        params.addBodyParameter("lastlogin", mLastLoginTime);
        params.addBodyParameter("explain", mDetailed);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                if (message.getCode() == 0) {
                    OBAlertDialog noticeDialogAbutton = new OBAlertDialog(CannotLoginActivity.this,
                            "", getResources().getString(R.string.tv_cannot_login_sub), "知道啦");
                    noticeDialogAbutton.setSure((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                            UIHelper.startActivity(CannotLoginActivity.this, LogAndRegActivity.class);
                        }
                    });
                    noticeDialogAbutton.show();

                } else {
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
