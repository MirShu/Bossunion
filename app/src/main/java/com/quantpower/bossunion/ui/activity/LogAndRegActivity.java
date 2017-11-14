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

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.base.BaseApplication;
import com.quantpower.bossunion.utils.ActivityManagerUtils;
import com.quantpower.bossunion.utils.UIHelper;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/2.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class LogAndRegActivity extends BaseActivity {
    @BindView(R.id.butt_register)
    Button buttRegister;
    @BindView(R.id.butt_sign_in)
    Button buttSignIn;


    @Override
    public int getContentViewId() {
        return R.layout.activity_logandreg;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        ActivityManagerUtils.getInstance().addActivity(this);
    }

    @OnClick({R.id.butt_register, R.id.butt_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butt_register:
                UIHelper.startActivity(LogAndRegActivity.this, RegisterActivity.class);
                break;
            case R.id.butt_sign_in:
                UIHelper.startActivity(LogAndRegActivity.this, LoginActivity.class);
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
