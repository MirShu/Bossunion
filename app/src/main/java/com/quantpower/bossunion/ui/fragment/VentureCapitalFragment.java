/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.CardPagerAdapter;
import com.quantpower.bossunion.adapter.LiveFragmentAdapter;
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.model.CoverItem;
import com.quantpower.bossunion.utils.Constants;
import com.quantpower.bossunion.utils.ShadowTransformer;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.zwlviewpager.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class VentureCapitalFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.ViewPager)
    android.support.v4.view.ViewPager viewPager;
    private List<CoverItem> mlist = new ArrayList<>();
    private int[] mImgs = {R.mipmap.icon_coff, R.mipmap.icon_yule, R.mipmap.bg_top01,
            R.mipmap.icon_yule, R.mipmap.icon_mation04, R.mipmap.bg_top02, R.mipmap.icon_jishu,
            R.mipmap.icon_yshi};

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @BindView(R.id.cardViewPager)
    ViewPager mCradViewPager;

    private List<String> mTitle = new ArrayList<>();
    private List<Fragment> mFragment = new ArrayList<>();


    public static VentureCapitalFragment newInstance(String s) {
        VentureCapitalFragment ventureCapitalFragment = new VentureCapitalFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        ventureCapitalFragment.setArguments(bundle);
        return ventureCapitalFragment;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_venture_capital;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initData();
        initView();
        mTitle.add("融资创投");
        mTitle.add("活动路演");
        mFragment.add(new EquityFragment());
        mFragment.add(new RoadShowsFragment());
        LiveFragmentAdapter adapter = new LiveFragmentAdapter(getActivity().getSupportFragmentManager(),
                mTitle, mFragment);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(adapter);
        tabs.post(() -> setIndicator(tabs, 60, 60));

    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < mImgs.length; i++) {
            CoverItem item = new CoverItem();
            item.setImg(mImgs[i]);
            item.setName(i + "km");
            mlist.add(item);
        }
    }

    private void initView() {
        mCardAdapter = new CardPagerAdapter(getActivity(), mlist);
        mCardShadowTransformer = new ShadowTransformer(mCradViewPager, mCardAdapter);
        float scalNum = (float) ((30 / 100f) * 0.5);
        mCardShadowTransformer.setScale(scalNum, true);  //选中是否缩小放大
        float mAlplaNum = 1 - (float) ((50 / 100f) * 0.6 + 0.1f);
        mCardShadowTransformer.setAlpha(mAlplaNum, true);//选中是否透明值
        mCradViewPager.setAdapter(mCardAdapter);
        mCradViewPager.setPageTransformer(false, mCardShadowTransformer);
        mCradViewPager.setOffscreenPageLimit(4);
        mCardAdapter.setOnItemClickListener(position -> UIHelper.toastMessage(getActivity(), "点击" + position));
    }


    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
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
