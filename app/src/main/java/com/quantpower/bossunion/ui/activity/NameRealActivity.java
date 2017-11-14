package com.quantpower.bossunion.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.IndustryModel;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.OBAlertDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

import static android.R.attr.type;

public class NameRealActivity extends BaseActivity {
    @BindView(R.id.card_img)
    RoundedImageView myCardImg;
    @BindView(R.id.ll_add_card)
    LinearLayout addCard;
    @BindView(R.id.iv_delete_card)
    ImageView deleteCard;
    @BindView(R.id.iv_invite_delete)
    ImageView deleteinvite;
    @BindView(R.id.et_invita_code)
    EditText inviteCode;
    @BindView(R.id.et_user_name)
    EditText userName;
    @BindView(R.id.et_user_company)
    EditText userCompany;
    @BindView(R.id.rl_add_card)
    RelativeLayout rlAddCard;
    @BindView(R.id.et_user_job)
    EditText userjob;
    @BindView(R.id.cb_invite_code)
    CheckBox cbInviteCode;
    @BindView(R.id.cb_add_card)
    CheckBox cbAddCard;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;

    private static final int CAMERA = 100;
    private static final int PICTURE = 200;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    private LinearLayout rl_head;
    private TextView tv_name;
    private TextView tv_state;
    private String mName;
    private String mserJob;
    private String mCompany;
    private String mInviteCode;
    private String mCode;
    private String mPhone;
    private String path;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //头像1
    //调用照相机返回图片临时文件
    private File tempFile;

    private String mIndustry;

    @Override
    public int getContentViewId() {
        return R.layout.activity_name_real;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("实名认证");
        //创建拍照存储的临时文件
        mIndustry = this.getIntent().getStringExtra("INDUSTRY");
        mCode = this.getIntent().getStringExtra("CODE");
        mPhone = this.getIntent().getStringExtra("PHONE");
        createCameraTempFile(savedInstanceState);

        UIHelper.toastMessage(NameRealActivity.this, mIndustry + "");

        tvAddCard.setOnClickListener(null);
        tvInviteCode.setOnClickListener(null);
        tvInviteCode.setVisibility(View.VISIBLE);
        tvAddCard.setVisibility(View.GONE);
        inviteCode.setFocusableInTouchMode(false);
        cbAddCard.setClickable(false);
        cbInviteCode.setClickable(true);

    }

    @OnClick({R.id.image_back, R.id.card_img, R.id.ll_add_card, R.id.iv_delete_card, R.id.iv_invite_delete, R.id.butt_next_step,
            R.id.cb_add_card, R.id.cb_invite_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;

            case R.id.ll_add_card:
//                UIHelper.startActivity(NameRealActivity.this, ClipImageActivity.class);
                uploadHeadImage();

                break;
            case R.id.iv_delete_card:
                myCardImg.setImageBitmap(null);
                addCard.setVisibility(View.VISIBLE);
                deleteCard.setVisibility(View.GONE);
                break;
            case R.id.iv_invite_delete:
                inviteCode.setText("");
                break;


            case R.id.cb_add_card:
                cbInviteCode.setChecked(false);
                tvInviteCode.setVisibility(View.VISIBLE);
                tvAddCard.setVisibility(View.GONE);
                cbAddCard.setClickable(false);
                cbInviteCode.setClickable(true);
                inviteCode.setText("");
                break;

            case R.id.cb_invite_code:
                cbAddCard.setChecked(false);
                tvAddCard.setVisibility(View.VISIBLE);
                tvInviteCode.setVisibility(View.GONE);
                inviteCode.setFocusableInTouchMode(true);
                cbInviteCode.setClickable(false);
                cbAddCard.setClickable(true);
                break;
            case R.id.butt_next_step:
                path = tempFile.toString();
                mName = userName.getText().toString().trim();
                mserJob = userjob.getText().toString().trim();
                mCompany = userCompany.getText().toString().trim();
                mInviteCode = inviteCode.getText().toString().trim();
                if (TextUtils.isEmpty(userCompany.getText()) || TextUtils.isEmpty(userjob.getText()) || TextUtils.isEmpty(userName.getText())) {
                    UIHelper.toastMessage(NameRealActivity.this, "请填写完整资料");
                } else {
                    if (cbAddCard.isChecked()) {
                        //判断是否有头像
                        if (!tempFile.exists()) {
                            UIHelper.toastMessage(NameRealActivity.this, "请上传名片哦");
                        } else {
                            cardSubmit();
//                            UIHelper.toastMessage(NameRealActivity.this, "跳转");
                        }
                    } else if (cbInviteCode.isChecked()) {
                        if (TextUtils.isEmpty(inviteCode.getText())) {
                            UIHelper.toastMessage(NameRealActivity.this, "请输入验证码");
                        } else {
                            inviteCodeSubmit();
                        }
                    } else {
//                        UIHelper.toastMessage(NameRealActivity.this, "跳转");

                    }
                }
                break;

        }
    }


    /**
     * 携带验证码提交注册
     */
    public void inviteCodeSubmit() {
        RequestParams parames = new RequestParams(URLS.USER_USER_REGISTER);
        parames.addBodyParameter("phone", mPhone);
        parames.addBodyParameter("code", mCode);
        parames.addBodyParameter("regid", "2622222");
        parames.addBodyParameter("register_yqm", mInviteCode);
        parames.addBodyParameter("trade_id", mIndustry);
        parames.addBodyParameter("name", mName);
        parames.addBodyParameter("position", mserJob);
        parames.addBodyParameter("company", mCompany);
        x.http().post(parames, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                if (message.getCode() == 0) {
                    OBAlertDialog noticeDialogAbutton = new OBAlertDialog(NameRealActivity.this,
                            "", getResources().getString(R.string.tv_registered_su),
                            "知道啦");
                    noticeDialogAbutton.setSure((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                            UIHelper.startActivity(NameRealActivity.this, LogAndRegActivity.class);
                        }
                    });
                    noticeDialogAbutton.show();

                } else {
                    UIHelper.toastMessage(NameRealActivity.this, message.getMsg());
                }
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
        });
    }

    /**
     * 携带名片提交注册
     */
    public void cardSubmit() {
        RequestParams parames = new RequestParams(URLS.USER_USER_REGISTER);
        parames.addBodyParameter("phone", mPhone);
        parames.addBodyParameter("code", mCode);
        parames.addBodyParameter("regid", "2622222");
        parames.addBodyParameter("trade_id", mIndustry);
        parames.addBodyParameter("name", mName);
        parames.addBodyParameter("position", mserJob);
        parames.addBodyParameter("company", mCompany);
        parames.addBodyParameter("file", new File(path));
        x.http().post(parames, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                if (message.getCode() == 0) {
                    OBAlertDialog noticeDialogAbutton = new OBAlertDialog(NameRealActivity.this,
                            "", getResources().getString(R.string.tv_registered_su),
                            "知道啦");
                    noticeDialogAbutton.setSure((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                            UIHelper.startActivity(NameRealActivity.this, LogAndRegActivity.class);
                        }
                    });
                    noticeDialogAbutton.show();

                } else {
                    UIHelper.toastMessage(NameRealActivity.this, message.getMsg());
                }
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
        });
    }



    /**
     * 外部存储权限申请返回
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCarema();
            } else {
                // Permission Denied
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            } else {
                // Permission Denied
            }
        }
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.qqLayout:
//                type = 1;
//                uploadHeadImage();
//                break;
//            case R.id.weixinLayout:
//                type = 2;
//                uploadHeadImage();
//                break;
//        }
//    }


    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_camera, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(() -> {
            params.alpha = 1.0f;
            getWindow().setAttributes(params);
        });

        btnCarema.setOnClickListener(v -> {
            //权限判断
            if (ContextCompat.checkSelfPermission(NameRealActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(NameRealActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                //跳转到调用系统相机
                gotoCarema();
            }
            popupWindow.dismiss();
        });
        btnPhoto.setOnClickListener(v -> {
            //权限判断
            if (ContextCompat.checkSelfPermission(NameRealActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请READ_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(NameRealActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                //跳转到调用系统图库
                gotoPhoto();
            }
            popupWindow.dismiss();
        });
        btnCancel.setOnClickListener(v -> popupWindow.dismiss());
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
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
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 1) {
                        myCardImg.setImageBitmap(bitMap);
                        saveBitmapFile(bitMap);
                        deleteCard.setVisibility(View.VISIBLE);
                    } else {
                        myCardImg.setImageBitmap(bitMap);
                        saveBitmapFile(bitMap);
                        deleteCard.setVisibility(View.VISIBLE);
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......

                }
                break;
        }
    }


    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    //将bitmap转换成文件
    public void saveBitmapFile(Bitmap bitmap) {
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (myCardImg.getDrawable() == null) {
            addCard.setVisibility(View.VISIBLE);
            deleteCard.setVisibility(View.GONE);
        } else {
            addCard.setVisibility(View.GONE);
            deleteCard.setVisibility(View.VISIBLE);
        }
        JAnalyticsInterface.onPageStart(getApplicationContext(), this.getClass().getCanonicalName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getApplicationContext(), this.getClass().getCanonicalName());
    }
}
