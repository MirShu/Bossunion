/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.ui.activity.MeCapitalActivity;
import com.quantpower.bossunion.ui.activity.MeDetailsActivity;
import com.quantpower.bossunion.ui.activity.MeOrderActivity;
import com.quantpower.bossunion.ui.activity.MeSettingActivity;
import com.quantpower.bossunion.ui.activity.MeVisitActivity;
import com.quantpower.bossunion.ui.activity.MyAttentionActivity;
import com.quantpower.bossunion.ui.activity.MyDynamicActivity;
import com.quantpower.bossunion.utils.Constants;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.ShareDialog;
import com.quantpower.bossunion.widget.extend.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.tv_visit_clues)
    TextView tvVisitClues;
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_PICK = 101;
    private static final int REQUEST_CROP_PHOTO = 102;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    Unbinder unbinder;
    //    @BindView(R.id.videoview)
//    WelcomeVideoView videoview;
    private File tempFile;
    private int type;

    public static MeFragment newInstance(String s) {
        MeFragment homeFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_me;

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        videoview.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.spread));
//        videoview.start();
//        videoview.setOnCompletionListener(mediaPlayer -> videoview.start());
    }

    @OnClick({R.id.tv_me_details, R.id.tv_me_capital, R.id.tv_me_order, R.id.tv_me_visit,
            R.id.tv_me_share, R.id.tv_me_setting, R.id.ll_attention, R.id.ll_dynamic, R.id.rl_banner,
            R.id.login_qq, R.id.login_ww, R.id.login_dd, R.id.login_facebook, R.id.login_pay, R.id.login_sina, R.id.login_tiwtter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_me_details:
                UIHelper.startActivity(getActivity(), MeDetailsActivity.class);
                break;
            case R.id.tv_me_capital:
                UIHelper.startActivity(getActivity(), MeCapitalActivity.class);
                break;
            case R.id.tv_me_order:
                UIHelper.startActivity(getActivity(), MeOrderActivity.class);
                break;
            case R.id.tv_me_visit:
                UIHelper.startActivity(getActivity(), MeVisitActivity.class);
                tvVisitClues.setVisibility(View.GONE);
                break;
            case R.id.tv_me_share:
                showShare();
                break;
            case R.id.tv_me_setting:
                UIHelper.startActivity(getActivity(), MeSettingActivity.class);
                break;
            case R.id.ll_attention:
                UIHelper.startActivity(getActivity(), MyAttentionActivity.class);
                break;
            case R.id.ll_dynamic:
                UIHelper.startActivity(getActivity(), MyDynamicActivity.class);
                break;
            case R.id.rl_banner:
                addHeadImage();
                break;
            case R.id.login_qq:
                break;
            case R.id.login_ww:
                break;
            case R.id.login_dd:
                break;
            case R.id.login_facebook:
                break;
            case R.id.login_pay:
                break;
            case R.id.login_sina:
                break;
            case R.id.login_tiwtter:
                break;

        }
    }


    public void addHeadImage() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_camera, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(getActivity()).inflate(R.layout.activity_me_details, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(() -> {
            params.alpha = 1.0f;
            getActivity().getWindow().setAttributes(params);
        });

        btnCarema.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                gotoCarema();
            }
            popupWindow.dismiss();
        });
        btnPhoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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

    /**
     * 分享功能
     */
    private void showShare() {
        new ShareDialog(getActivity()).builder().setTitle("分享到").setCancelButton(v -> {
        }).ShareWechat(v -> ToastUtil.showShareToast(getActivity(), R.mipmap.icon_fail, "分享失败")).ShareWechatmoments(v -> {
            ToastUtil.showShareToast(getActivity(), R.mipmap.icon_success, "分享成功");
        }).ShareSinaweibo(v -> ToastUtil.showShareToast(getActivity(), R.mipmap.icon_fail, "分享失败")).ShareQq(v -> {
            ToastUtil.showShareToast(getActivity(), R.mipmap.icon_success, "分享成功");
        }).ShareQzone(v -> ToastUtil.showShareToast(getActivity(), R.mipmap.icon_success, "分享成功")).show();
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
