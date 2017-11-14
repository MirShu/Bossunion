/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pgyersdk.crash.PgyCrashManager;
import com.quantpower.bossunion.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class BaseApplication extends Application {
    public static List<?> images = new ArrayList<>();
    public static List<?> ventureImages = new ArrayList<>();
    public static List<String> titles = new ArrayList<>();
    public static int H, W;
    public static Context context = null;
    public ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        getScreen(this);
        x.Ext.init(this);
        x.Ext.setDebug(false); //是否输出debug日子,开启会影响性能
        Fresco.initialize(this);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));  //处理"ImageLoader must be init with configuration before using"的报错
        context = getApplicationContext();
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        String[] vBanner = getResources().getStringArray(R.array.url4);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        PgyCrashManager.register(getApplicationContext());
        JAnalyticsInterface.init(this);
        JPushInterface.setDebugMode(false);
        JAnalyticsInterface.initCrashHandler(this);
        JAnalyticsInterface.setDebugMode(true);
        JPushInterface.init(this);
        List list0 = Arrays.asList(vBanner);
        ventureImages = new ArrayList(list0);

        List list1 = Arrays.asList(tips);
        titles = new ArrayList(list1);


//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

}
