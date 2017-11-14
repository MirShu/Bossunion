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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.LiveFragmentAdapter;
import com.quantpower.bossunion.ui.fragment.CurrentAffairsFragment;
import com.quantpower.bossunion.ui.fragment.FinanceFragment;
import com.quantpower.bossunion.ui.fragment.FocuseFrgment;
import com.quantpower.bossunion.widget.dialog.LodingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ShuLin on 2017/6/22.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class TopicsFocusedActivity extends AppCompatActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.ViewPager)
    ViewPager ViewPager;
    private List<String> mTitle = new ArrayList<>();
    private List<Fragment> mFragment = new ArrayList<>();
    LodingDialog lodingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_focused);
        unbinder = ButterKnife.bind(this);
        tvMainTitle.setText("焦点聚集");
        initView();
        lodingDialog = new LodingDialog(TopicsFocusedActivity.this, "加载中...");
        lodingDialog.show();
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
    }

    private void initView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        ViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mTitle.add("实时焦点");
        mTitle.add("财经热点");
        mTitle.add("时事精选");

        mFragment.add(new FocuseFrgment());
        mFragment.add(new FinanceFragment());
        mFragment.add(new CurrentAffairsFragment());

        LiveFragmentAdapter adapter = new LiveFragmentAdapter(getSupportFragmentManager(),
                mTitle, mFragment);
        ViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(ViewPager);
        tabs.setTabsFromPagerAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_back_icon)
    public void onViewClicked() {
        finish();
    }
}
