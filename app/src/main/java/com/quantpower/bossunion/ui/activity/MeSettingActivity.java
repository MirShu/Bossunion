/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.widget.dialog.DeleteMyLabelDialog;
import com.quantpower.bossunion.widget.dialog.TBAlertDialog;
import com.quantpower.bossunion.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/3.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MeSettingActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageView ivBackIcon;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("设置");
        String version = "V" + getAppVersionName(MeSettingActivity.this);
        this.tvVersion.setText(version);
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.tv_alerts, R.id.tv_clear_the_cache, R.id.tv_about_us, R.id.tv_version_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_alerts:
                UIHelper.startActivity(MeSettingActivity.this, NoticeSetActivity.class);
                break;
            case R.id.tv_clear_the_cache:
                clearCacheDialog();
                break;
            case R.id.tv_about_us:
                UIHelper.startActivity(MeSettingActivity.this, AboutUsActivity.class);
                break;
            case R.id.tv_version_name:
                viewUpdateCustom();
                break;
        }
    }

    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

        }
        return versionName;
    }

    //清除缓存
    private void clearCacheDialog() {
        new TBAlertDialog(MeSettingActivity.this).builder().setTitle("提示")
                .setMsg("确定清除缓存？")
                .setPositiveButton("确认", v -> {
                    UIHelper.toastMessage(MeSettingActivity.this, "清除成功");
                }).setNegativeButton("取消", v -> {
        }).show();
    }

    private void viewUpdateCustom() {
        PgyUpdateManager.register(MeSettingActivity.this, "您自定义provider file值",
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        final AppBean appBean = getAppBeanFromString(result);
                        DeleteMyLabelDialog noticeDialogAbutton = new DeleteMyLabelDialog(MeSettingActivity.this, "发现新版本",
                                "^_^小鳝鳝更新新版本了^_^\n" +
                                        "1.修复界面请求网络数据卡顿\n" +
                                        "2.聚焦话题完善\n" +
                                        "3.聚焦话题分三大模块，实时焦点，财经热点，时事精选\n" +
                                        "4.我的订单调整显示功能\n" +
                                        "5.我的最新来访实时数据\n" +
                                        "6.优化列表嵌套滑动不流畅bug\n" +
                                        "7.话题聚焦详情界面完善\n" +
                                        "8.增加IM背景墙纸", "取消",
                                "确认更新");
                        noticeDialogAbutton.setSure((dialog, flag) -> {
                            if (flag) {
                                startDownloadTask(MeSettingActivity.this,
                                        appBean.getDownloadURL());
                            }
                        });
                        noticeDialogAbutton.setCancel((dialog, flag) -> {
                            if (flag) {
                                noticeDialogAbutton.dismiss();
                            }
                        });
                        noticeDialogAbutton.show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        UIHelper.toastMessage(MeSettingActivity.this, "暂无新版本");
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

