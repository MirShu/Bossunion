package com.quantpower.bossunion.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.quantpower.bossunion.base.BaseApplication;

/**
 * Created by likai on 2017/5/3.
 * email: codingkai@163.com
 * 图片缩放工具类
 */

public class UIUtils {

  public static int getColor(int colorId) {
    return getContext().getResources().getColor(colorId);
  }

  public static View getXmlView(int layoutId) {
    return View.inflate(getContext(), layoutId, null);
  }

  public static String[] getStringArr(int arrId) {
    return getContext().getResources().getStringArray(arrId);
  }

  /**
   * 1dp---1px;
   * 1dp---0.75px;
   * 1dp---0.5px;
   * ....
   *
   * @param dp
   * @return
   */
  public static int dp2px(int dp) {
    float density = getContext().getResources().getDisplayMetrics().density;
    return (int) (dp * density + 0.5);
  }

  ;

  public static int px2dp(int px) {
    float density = getContext().getResources().getDisplayMetrics().density;
    return (int) (px / density + 0.5);
  }

  public static Context getContext() {
    return BaseApplication.context;
  }





  public static void Toast(String text, boolean isLong) {
    Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
  }
}
