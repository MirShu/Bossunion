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
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.widget.extend.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * 个人介绍
 */

public class DTPersonalIntroductionActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;

    @Override
    public int getContentViewId() {
        return R.layout.activity_dt_introduction;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("个人介绍");

    }

    @OnClick({R.id.image_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_save:
                ToastUtil.showShareToast(DTPersonalIntroductionActivity.this, R.mipmap.icon_success, "保存成功");
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
