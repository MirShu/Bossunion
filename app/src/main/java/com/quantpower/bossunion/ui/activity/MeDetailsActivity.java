/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/6.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MeDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_PICK = 101;
    private static final int REQUEST_CROP_PHOTO = 102;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private File tempFile;
    private int type;

    @Override
    public int getContentViewId() {
        return R.layout.activity_me_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("完善资料");

    }

    @OnClick({R.id.image_back, R.id.tv_dt_industy, R.id.tv_dt_label, R.id.tv_dt_introduction, R.id.circle_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_head:
                addHeadImage();
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_dt_industy:
                UIHelper.startActivity(MeDetailsActivity.this, DTModifyIndustry.class);
                break;
            case R.id.tv_dt_label:
                UIHelper.startActivity(MeDetailsActivity.this, DTMyLabelActivity.class);
                break;
            case R.id.tv_dt_introduction:
                UIHelper.startActivity(MeDetailsActivity.this, DTPersonalIntroductionActivity.class);
                break;
        }
    }

    public void addHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_camera, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_me_details, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(() -> {
            params.alpha = 1.0f;
            getWindow().setAttributes(params);
        });

        btnCarema.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(MeDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MeDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                gotoCarema();
            }
            popupWindow.dismiss();
        });
        btnPhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(MeDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MeDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                gotoPhoto();
            }
            popupWindow.dismiss();
        });
        btnCancel.setOnClickListener(v -> popupWindow.dismiss());
    }

    /**
     * 跳转到照相机
     */
    private void gotoCarema() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
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
