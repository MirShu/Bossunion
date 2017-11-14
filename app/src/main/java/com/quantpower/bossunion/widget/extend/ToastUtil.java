/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.widget.extend;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quantpower.bossunion.R;

/**
 * Created by ShuLin on 2017/5/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ToastUtil extends Toast {
    private static Toast mToast;

    public ToastUtil(Context context) {
        super(context);
    }

    /**
     * 自定义Toast样式
     *
     * @param context
     * @param resId
     * @param text
     * @param duration
     * @description
     */
    public static Toast makeText(Context context, int resId, CharSequence text,
                                 int duration) {
        Toast result = new Toast(context);

        // 获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.view_toast_tort, null);

        // 实例化ImageView和TextView对象
        ImageView imageView = (ImageView) layout.findViewById(R.id.ImageView);
        TextView textView = (TextView) layout.findViewById(R.id.message);
        layout.getBackground().setAlpha(75);
        //这里我为了给大家展示就使用这个方面既能显示无图也能显示带图的toast
        if (resId == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(resId);
        }
        textView.setText(text);
        result.setView(layout);
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public static void showToast(Context context, int resId, String content) {

        if (mToast == null) {
            mToast = Toast.makeText(context, content, resId);
        } else {
            mToast.setText(content);
            mToast.setDuration(resId);
        }

        mToast.show();


        mToast = ToastUtil.makeText(context, resId, content, 100);
        mToast.show();
    }

    public static Toast makeShareText(Context context, int resId, CharSequence text,
                                      int duration) {
        Toast result = new Toast(context);

        // 获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.view_toast_share, null);

        // 实例化ImageView和TextView对象
        ImageView imageView = (ImageView) layout.findViewById(R.id.ImageView);
        TextView textView = (TextView) layout.findViewById(R.id.message);
        RelativeLayout rl_toast = (RelativeLayout) layout.findViewById(R.id.rl_toast);
        rl_toast.getBackground().setAlpha(178);

        //这里我为了给大家展示就使用这个方面既能显示无图也能显示带图的toast
        if (resId == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(resId);
        }

        textView.setText(text);

        result.setView(layout);
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public static void showShareToast(Context context, int resId, String content) {

        mToast = ToastUtil.makeShareText(context, resId, content, 100);
        mToast.show();

    }
}
