package com.quantpower.bossunion.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.ui.fragment.NavigationFragment;
import com.quantpower.bossunion.utils.ActivityManagerUtils;
import com.quantpower.bossunion.utils.BackHandlerHelper;
import com.quantpower.bossunion.widget.dialog.DeleteMyLabelDialog;
import com.quantpower.bossunion.widget.dialog.TBAlertDialog;
import com.quantpower.bossunion.utils.UIHelper;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationFragment mNavigationFragment;
    private long mExitTime;
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PgyCrashManager.register(this);
        viewUpdateCustom();
        getSupportActionBar().hide();
        setCurrentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mNavigationFragment == null) {
            mNavigationFragment = NavigationFragment.newInstance(getString(R.string.navigation_navigation_bar));
        }
        transaction.replace(R.id.frame_content, mNavigationFragment);

    }

    public interface FragmentBackHandler {
        boolean onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if ((System.currentTimeMillis() - mExitTime) < 2000) {
                super.onBackPressed();

            } else {
                UIHelper.toastMessage(MainActivity.this, "再按一次退出程序");
                ActivityManagerUtils.getInstance().exit();
            }
        }
    }


    private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNavigationFragment = NavigationFragment.newInstance(getString(R.string.navigation_navigation_bar));
        transaction.replace(R.id.frame_content, mNavigationFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
        }
        transaction.commit();
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    private void viewUpdateCustom() {
        PgyUpdateManager.register(MainActivity.this, null,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        final AppBean appBean = getAppBeanFromString(result);
                        DeleteMyLabelDialog noticeDialogAbutton = new DeleteMyLabelDialog(MainActivity.this, "发现新版本",
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
                                startDownloadTask(MainActivity.this,
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

                    }
                });
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        JAnalyticsInterface.onPageStart(getApplicationContext(), this.getClass().getCanonicalName());
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        JAnalyticsInterface.onPageEnd(getApplicationContext(), this.getClass().getCanonicalName());
    }

}
