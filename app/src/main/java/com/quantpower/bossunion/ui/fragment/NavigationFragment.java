/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.utils.Constants;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class NavigationFragment extends Fragment implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar mBottomNavigationBar;
    private HomeFragment mHomeFragment;
    private DiscoveryFragment mDiscoveryFragment;
    private InformationFragment mInformationFragment;
    private VentureCapitalFragment ventureCapitalFragment;
    private MeFragment mMeFragment;
    private BadgeItem badge;

    public static NavigationFragment newInstance(String s) {
        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation_bar, container, false);
        mBottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        badge = new BadgeItem()
                .setBorderWidth(2)//Badge的Border(边界)宽度
                .setBorderColor("#FF0000")//Badge的Border颜色
                .setBackgroundColor("#FF0000")//Badge背景颜色
                .setGravity(Gravity.RIGHT | Gravity.TOP)//位置，默认右上角
                .setHideOnSelect(false)//当选中状态时消失，非选中状态显示
                .setText("8")//显示的文本
                .setTextColor("#F0F8FF");//文本颜色
//                .setAnimationDuration(2000) //消失动画时间

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home_fill, getString(R.string.item_home))
                .setInactiveIconResource(R.mipmap.home)
                .setActiveColorResource(R.color.color_000000)
                .setInActiveColorResource(R.color.color_c0c0c0))
                .addItem(new BottomNavigationItem(R.mipmap.information_fill, getString(R.string.item_information))
                        .setInactiveIconResource(R.mipmap.information)
                        .setActiveColorResource(R.color.color_000000)
                        .setInActiveColorResource(R.color.color_c0c0c0).setBadgeItem(badge))
                .addItem(new BottomNavigationItem(R.drawable.discoveryfill, getString(R.string.item_discovery))
                        .setInactiveIconResource(R.drawable.discovery)
                        .setActiveColorResource(R.color.color_000000)
                        .setInActiveColorResource(R.color.color_c0c0c0))

                .addItem(new BottomNavigationItem(R.drawable.venturecapital, getString(R.string.item_venture_capital))
                        .setInactiveIconResource(R.drawable.venturecapital)
                        .setActiveColorResource(R.color.color_000000)
                        .setInActiveColorResource(R.color.color_c0c0c0))

                .addItem(new BottomNavigationItem(R.mipmap.me_fill, getString(R.string.item_me))
                        .setInactiveIconResource(R.mipmap.me)
                        .setActiveColorResource(R.color.color_000000)
                        .setInActiveColorResource(R.color.color_c0c0c0))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
        return view;
    }

    /**
     * set the default fagment
     * <p>默认显示的Fragment
     * the content id should not be same with the parent content id
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        HomeFragment homeFragment = HomeFragment.newInstance(getString(R.string.item_home));
        transaction.replace(R.id.sub_content, homeFragment).commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        try {
            switch (position) {
                case 0:
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance(getString(R.string.item_home));
                    }
                    beginTransaction.replace(R.id.sub_content, mHomeFragment);
                    break;
                case 1:

                    if (mInformationFragment == null) {
                        mInformationFragment = InformationFragment.newInstance(getString(R.string.item_information));
                    }
                    beginTransaction.replace(R.id.sub_content, mInformationFragment);
                    break;

                case 2:
                    if (mDiscoveryFragment == null) {
                        mDiscoveryFragment = DiscoveryFragment.newInstance(getString(R.string.item_discovery));
                    }
                    beginTransaction.replace(R.id.sub_content, mDiscoveryFragment);
                    break;

                case 3:

                    if (ventureCapitalFragment == null) {
                        ventureCapitalFragment = VentureCapitalFragment.newInstance(getString(R.string.item_venture_capital));
                    }
                    beginTransaction.replace(R.id.sub_content, ventureCapitalFragment);
                    break;

                case 4:
                    if (mMeFragment == null) {
                        mMeFragment = MeFragment.newInstance(getString(R.string.item_me));
                    }
                    beginTransaction.replace(R.id.sub_content, mMeFragment);
                    break;
            }
            beginTransaction.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

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
