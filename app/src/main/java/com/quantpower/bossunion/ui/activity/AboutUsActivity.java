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
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;

import org.apache.http.util.EncodingUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.wv_about)
    WebView wvAbout;
    String loadUrl = "<div style=\"color:white'><img alt=\"\" src=\"http://118.178.121.211/Public/attached/image/20170328/20170328073736_57795.jpg\" /> " +
            "量子财经，是一款专注于全球金融市场，定<span style=\"line-height:3;\">" +
            "</span>位于专业财经阅读的手机资讯软件，以时效性快，见知性高深受广大读者喜爱，" +
            "目前已成为中国领先的互联网金融服务提供商。\n" +
            "股票、期货、外汇、大宗商品、贵金属、原油……一个都不落！\n如有商务合作、" +
            "意见反馈等可联系以下方式：\n" +
            "官方邮箱：app@quant-power.com\nQQ联系：" +
            "<a href=\"http://wpa.qq.com/msgrd?v=3&uin=2880977679&site=qq&menu=yes\" target=\"white\">2880977679</a> " +
            "\n</p>\r\n电话：021-63310115<br /></div>";

    @Override
    public int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("关于波士界");
        wvAbout.getSettings().setJavaScriptEnabled(true);
        wvAbout.getSettings().setDefaultTextEncodingName("utf-8");
        wvAbout.setBackgroundColor(0);
        loadUrl = loadUrl.replace("style", "");
        loadUrl = loadUrl.replace("width", "").replace("<img", "<img style='max-width:100% '");
        loadUrl = "<style>a{color:white;text-decoration:none;}</style>" + loadUrl;
        String aboutContent = "<html><head><style type=\"text/css\">body " +
                "{text-align:justify; font-size:px; line-height: " + (30) + "px;color:#ffffff}</style></head> \n" +
                "<body>" + EncodingUtils.getString(loadUrl.getBytes(), "UTF-8") + "</body> \n </html>";
        wvAbout.loadDataWithBaseURL(null, aboutContent, "text/html", "utf-8", null);
        llBottom.getBackground().setAlpha(75);
    }

    @OnClick({R.id.image_back, R.id.tv_about_score, R.id.tv_about_opinion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_about_score:
                UIHelper.toastMessage(AboutUsActivity.this, "哈利路亚丫丫");
                break;
            case R.id.tv_about_opinion:
                UIHelper.toastMessage(AboutUsActivity.this, "哈利路亚丫丫");
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
