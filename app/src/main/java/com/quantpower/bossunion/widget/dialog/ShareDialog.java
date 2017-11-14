/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;

/**
 * Created by ShuLin on 2017/5/10.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class ShareDialog {

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private ImageView iv_pup_close;
    private TextView tv_share_wechat;
    private TextView tv_share_wechatmoments;
    private TextView tv_share_sinaweibo;
    private TextView tv_share_qq;
    private TextView tv_share_qzone;
    private Display display;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public ShareDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ShareDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_share_alertdialog, null);
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);

        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        iv_pup_close = (ImageView) view.findViewById(R.id.iv_pup_close);
        iv_pup_close.setVisibility(View.GONE);

        tv_share_wechat = (TextView) view.findViewById(R.id.tv_share_wechat);
        tv_share_wechatmoments = (TextView) view.findViewById(R.id.tv_share_wechatmoments);
        tv_share_sinaweibo = (TextView) view.findViewById(R.id.tv_share_sinaweibo);
        tv_share_qq = (TextView) view.findViewById(R.id.tv_share_qq);
        tv_share_qzone = (TextView) view.findViewById(R.id.tv_share_qzone);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.75), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public ShareDialog setTitle(String title) {
        showTitle = true;
        txt_title.setText(title);
        return this;
    }

    public ShareDialog setCancelButton(final View.OnClickListener listener) {
        showPosBtn = true;
//        btn_pos.setText("取消");
        iv_pup_close.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * 微信分享按钮
     */
    public ShareDialog ShareWechat(final View.OnClickListener listener) {
        showNegBtn = true;
        tv_share_wechat.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }


    /**
     * 微信朋友圈分享按钮
     */
    public ShareDialog ShareWechatmoments(final View.OnClickListener listener) {
        showNegBtn = true;
        tv_share_wechatmoments.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * 新浪分享按钮
     */
    public ShareDialog ShareSinaweibo(final View.OnClickListener listener) {
        showNegBtn = true;
        tv_share_sinaweibo.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * QQ好友分享按钮
     */
    public ShareDialog ShareQq(final View.OnClickListener listener) {
        showNegBtn = true;
        tv_share_qq.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * QQ空间分享按钮
     */
    public ShareDialog ShareQzone(final View.OnClickListener listener) {
        showNegBtn = true;
        tv_share_qzone.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }


    private void setLayout() {
        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && showNegBtn) {
            iv_pup_close.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
