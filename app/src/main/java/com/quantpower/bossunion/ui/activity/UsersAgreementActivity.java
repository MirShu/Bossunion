/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.MessageResult;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/4.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class UsersAgreementActivity extends BaseActivity {
  @BindView(R.id.tv_main_title)
  TextView tvMainTitle;
  @BindView(R.id.wv_agreement)
  WebView wvAgreement;

  @Override
  public int getContentViewId() {
    return R.layout.activity_users_agreement;
  }

  @Override
  protected void initAllMembersView(Bundle savedInstanceState) {
    tvMainTitle.setText(getResources().getString(R.string.tv_users_agreement));
    showWebView();
  }

  @OnClick(R.id.image_back)
  public void onViewClicked() {
    finish();
  }


  private void showWebView() {
    WebSettings wvSettings = wvAgreement.getSettings();
    wvSettings.setJavaScriptEnabled(true);
    wvSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    wvSettings.setAppCacheEnabled(true);
    wvSettings.setDomStorageEnabled(true);
    wvSettings.setDatabaseEnabled(true);
    wvSettings.setAllowFileAccess(true);
    wvAgreement.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    wvAgreement.getSettings().setDomStorageEnabled(true);
    wvAgreement.setOnLongClickListener(view -> true);
    wvAgreement.loadUrl("http://news.fx678.com/C/20170405/201704050838111799.shtml");
    getData();
  }

  public void getData() {
    RequestParams requestParams = new RequestParams();
    x.http().get(requestParams, new Callback.CommonCallback<String>() {
              @Override
              public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
//                        UsModel usModel = JSON.parseObject(message.getData(), UsModel.class);
//                        String newsContent = "<html><head><style type=\"text/css\">body " +
//                                "{text-align:justify; font-size:px; line-height: " + (30) + "px;color:#666666}</style></head> \n" +
//                                "<body>" + EncodingUtils.getString(usModel.getAbout_us().getBytes(), "UTF-8") + "</body> \n </html>";
//                        wv_about.loadDataWithBaseURL(null, getNewContent(newsContent),
//                                "text/html", "utf-8", null);
              }

              @Override
              public void onError(Throwable ex, boolean isOnCallback) {

              }

              @Override
              public void onCancelled(CancelledException cex) {

              }

              @Override
              public void onFinished() {

              }
            }
    );
  }


  private String getNewContent(String htmltext) {
    org.jsoup.nodes.Document doc = Jsoup.parse(htmltext);
    Elements elements = doc.getElementsByTag("img");
    for (org.jsoup.nodes.Element element : elements) {
      element.attr("width", "100%").attr("height", "auto");
    }
    return doc.toString();
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
