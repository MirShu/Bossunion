/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseApplication;
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.utils.Constants;
import com.quantpower.bossunion.widget.extend.GlideImageLoader;
import com.quantpower.bossunion.widget.scollview.HorizontalScrollViewEx;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.lang.reflect.Field;

import butterknife.BindView;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class DiscoveryFragment extends BaseFragment implements OnBannerListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tabs)
    HorizontalScrollViewEx scrollViewEx;
    @BindView(R.id.pager)
    ViewPager viewPager;

    private DisplayMetrics dm;
    private InformationFragment informationFragment;


    public static DiscoveryFragment newInstance(String s) {
        DiscoveryFragment homeFragment = new DiscoveryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        banner.setImages(BaseApplication.images)
                .setImageLoader(new GlideImageLoader())
                .setBannerTitles(BaseApplication.titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setOnBannerListener(this)
                .setBannerAnimation(ZoomOutSlideTransformer.class)
                .start();

        this.setOverflowShowingAlways();
        this.dm = getResources().getDisplayMetrics();
        this.viewPager.setOffscreenPageLimit(7);
        this.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        this.scrollViewEx.setViewPager(viewPager);
        setTabsValue();

    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity().getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }


    /**
     * 对HorizontalScrollViewEx的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        this.scrollViewEx.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        this.scrollViewEx.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
//        this.scrollViewEx.setUnderlineHeight((int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        this.scrollViewEx.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab标题文字的大小
        this.scrollViewEx.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, dm));
        // 设置Tab Indicator的颜色
//        this.scrollViewEx.setIndicatorColor(Color.parseColor("#d83737"));
        // 设置选中Tab文字的颜色 (自定义的一个方法)
        this.scrollViewEx.setSelectedTextColor(Color.parseColor("#333333"));
        // 取消点击Tab时的背景色
        this.scrollViewEx.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"私人包机", "海外生子", "旅游定制", "游艇租赁", "生日part", "庆生活动", "纪念日",
                "周年庆典"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return DiscoveryChannelFragment.newInstance(position);
        }

    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration
                    .get(getParentFragment().getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getActivity().getApplicationContext(), this.getClass().getCanonicalName());
    }


    @Override
    public void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getActivity().getApplicationContext(), this.getClass().getCanonicalName());
    }
}
