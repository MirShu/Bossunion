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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.widget.dialog.LodingDialog;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/6/22.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class TopicDetailsActivity extends BaseActivity {
    public static final String NEWSURL = "news_url";
    public static final String NEWTITLE = "title";
    @BindView(R.id.topic_data_web)
    WebView topicDataWeb;
    @BindView(R.id.topocs_title)
    TextView topocsTitle;
    String url;
    String title;
    LodingDialog lodingDialog;
    private WebSettings wvSettings;

    @Override
    public int getContentViewId() {
        return R.layout.activity_topic_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.url = getIntent().getStringExtra(NEWSURL);
        this.title = getIntent().getStringExtra(NEWTITLE);
        topocsTitle.setText(title);
        lodingDialog = new LodingDialog(TopicDetailsActivity.this, "加载中...");
        lodingDialog.show();
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
        topicDataWeb.setOnLongClickListener(view -> true);
        topicDataWeb.loadUrl(url);
        topicDataWeb.getSettings().setJavaScriptEnabled(true);
        topicDataWeb.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        topicDataWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        topicDataWeb.getSettings().setDomStorageEnabled(true);
        topicDataWeb.setWebViewClient(new WebViewClient(){
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.iv_back_icon)
    public void onViewClicked() {
        finish();
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
