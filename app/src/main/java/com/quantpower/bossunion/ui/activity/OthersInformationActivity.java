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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.FlowLayoutManager;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.ImageLoaderEx;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.utils.UIUtils;
import com.quantpower.bossunion.widget.extend.CircleImageView;
import com.quantpower.bossunion.widget.extend.WelcomeVideoView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class OthersInformationActivity extends BaseActivity implements OnBannerListener {
    public static final String SMALLPIC_URL = "news_id_tag";
    public static final String BIGPIC_URL = "news_type";
    public static final String ALLNUM = "allnum";
    public static final String ROOMID = "roomid";
    public static final String MYNAME = "name";
    @BindView(R.id.tv_allnum)
    TextView tvAllnum;
    @BindView(R.id.tv_roomid)
    TextView tvRoomid;
    @BindView(R.id.videoview)
    WelcomeVideoView videoview;
    private String smallpicUrl;
    private String bigpicUrl;
    private String allnum;
    private String roomid;
    private String name;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.imge_head)
    CircleImageView imgeHead;
    @BindView(R.id.recy_want)
    RecyclerView recyWant;
    @BindView(R.id.recy_provide)
    RecyclerView recyProvide;
    @BindView(R.id.recy_industry)
    RecyclerView recyIndustry;
    @BindView(R.id.tv_chat)
    com.rey.material.widget.TextView tvChat;
    @BindView(R.id.tv_focus_on)
    com.rey.material.widget.TextView tvFocusOn;

    private RecyclerAdapter industryAdapter;
    private RecyclerAdapter wantAdapter;
    private RecyclerAdapter provideAdapter;

    private List<String> industryList;
    private List<String> wantList;
    private List<String> provideList;

    private final int TEXTADDCOLOR = 0xff666666;

    @Override
    public int getContentViewId() {
        return R.layout.activity_others_information;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.smallpicUrl = getIntent().getStringExtra(SMALLPIC_URL);
        this.bigpicUrl = getIntent().getStringExtra(BIGPIC_URL);
        this.allnum = getIntent().getStringExtra(ALLNUM);
        this.roomid = getIntent().getStringExtra(ROOMID);
        this.name = getIntent().getStringExtra(MYNAME);
        if (bigpicUrl == null) {
            videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.spread));
            videoview.start();
            videoview.setOnCompletionListener(mediaPlayer -> videoview.start());
        } else {
            ImageLoader.getInstance().displayImage(bigpicUrl, this.ivBg, ImageLoaderEx.getDefaultDisplayImageOptions());
        }
        ImageLoader.getInstance().displayImage(smallpicUrl, this.imgeHead, ImageLoaderEx.getDefaultDisplayImageOptions());
        this.tvAllnum.setText(allnum);
        this.tvRoomid.setText(roomid);
        bindRecycleView();
        /**
         * 由于自定义的recycleview  manager 目前没有能力解决高度与 scrollview控件的冲突
         * 只能很low的根据条目的长度动态设置高度
         */
        if (industryList.size() == 3 && industryList.get(0).length() + industryList.get(1).length() + industryList.get(2).length() > 12) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyIndustry.getLayoutParams();
            params.height = UIUtils.dp2px(90);
            recyIndustry.setLayoutParams(params);
        }
        if (industryList.size() == 4 && industryList.get(0).length() + industryList.get(1).length() + industryList.get(2).length() + industryList.get(3).length() > 9) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyIndustry.getLayoutParams();
            params.height = UIUtils.dp2px(90);
            recyIndustry.setLayoutParams(params);
        }
        if (industryList.size() == 5 && industryList.get(0).length() + industryList.get(1).length() + industryList.get(2).length() + industryList.get(3).length() + industryList.get(4).length() > 9) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyIndustry.getLayoutParams();
            params.height = UIUtils.dp2px(90);
            recyIndustry.setLayoutParams(params);
        }

        if (wantList.size() == 3 && wantList.get(0).length() + wantList.get(1).length() + wantList.get(2).length() > 12) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyWant.getLayoutParams();
            params.height = UIUtils.dp2px(90);
            recyWant.setLayoutParams(params);
        }

        if (provideList.size() == 3 && provideList.get(0).length() + provideList.get(1).length() + provideList.get(2).length() > 12) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyProvide.getLayoutParams();
            params.height = UIUtils.dp2px(90);
            recyProvide.setLayoutParams(params);
        }
    }

    @Override
    public void OnBannerClick(int position) {

    }

    private void bindRecycleView() {
        this.getIndustryData();
        this.getWantData();
        this.getProvideData();
        this.industryAdapter = new RecyclerAdapter<String>(OthersInformationActivity.this, industryList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                helper.setText(R.id.tv_title, industryList.get(position));

                mTitle.setTextColor(TEXTADDCOLOR);
            }
        };
        this.wantAdapter = new RecyclerAdapter<String>(OthersInformationActivity.this, wantList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                helper.setText(R.id.tv_title, wantList.get(position));

                mTitle.setTextColor(TEXTADDCOLOR);
            }
        };
        this.provideAdapter = new RecyclerAdapter<String>(OthersInformationActivity.this, provideList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                helper.setText(R.id.tv_title, provideList.get(position));
                mTitle.setTextColor(TEXTADDCOLOR);
            }
        };


        this.recyIndustry.setHasFixedSize(true);
        this.recyIndustry.setLayoutManager(new FlowLayoutManager());
        this.recyIndustry.setAdapter(this.industryAdapter);

        this.recyWant.setHasFixedSize(true);
        this.recyWant.setLayoutManager(new FlowLayoutManager());
        this.recyWant.setAdapter(this.wantAdapter);

        this.recyProvide.setHasFixedSize(true);
        this.recyProvide.setLayoutManager(new FlowLayoutManager());
        this.recyProvide.setAdapter(this.provideAdapter);

    }


    private void getIndustryData() {
        industryList = new ArrayList<>();
        industryList.add("金融行业");
        industryList.add("海关一号");
        industryList.add("天使投资");
        industryList.add("魔鬼投资");
        industryList.add("好一个投资");
    }

    private void getWantData() {
        wantList = new ArrayList<>();
        wantList.add("金融行业");
        wantList.add("海关一号");
        wantList.add("天使投资");
    }

    private void getProvideData() {
        provideList = new ArrayList<>();
        provideList.add("金融行业");
        provideList.add("海关一号");
        provideList.add("天使投资");
    }

    @OnClick({R.id.tv_chat, R.id.tv_focus_on, R.id.ll_attention, R.id.ll_dynamic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat:
                Bundle bundle = new Bundle();
                bundle.putString(ChatRoomActivity.MYNAME, name);
                bundle.putString(ChatRoomActivity.SMALLPICURL, smallpicUrl);
                bundle.putString(ChatRoomActivity.BIGPICURL, bigpicUrl);
                UIHelper.startActivity(OthersInformationActivity.this, ChatRoomActivity.class, bundle);
                break;
            case R.id.tv_focus_on:
                UIHelper.toastMessage(OthersInformationActivity.this, "已关注");
                break;
            case R.id.ll_attention:
                UIHelper.startActivity(OthersInformationActivity.this, MyAttentionActivity.class);
                break;
            case R.id.ll_dynamic:
                UIHelper.startActivity(OthersInformationActivity.this, MyDynamicActivity.class);
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
