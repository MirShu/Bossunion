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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantpower.bossunion.R;

/**
 * Created by Likai on 2017/5/3.
 */

public class DeleteMyLabelDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView txtNotice;
    private TextView txtOk;
    private TextView tvTitle;
    private DialogSure _sure;
    private DialogCancel _cancel;
    private TextView txtDelete;


    public interface DialogSure {
        public abstract void onSureResult(DeleteMyLabelDialog dialog, boolean flag);
    }

    public interface DialogCancel {
        public abstract void onCancelResult(DeleteMyLabelDialog dialog, boolean flag);
    }

    public void setSure(DialogSure callSure) {
        _sure = callSure;
    }

    public void setCancel(DialogCancel callCancel) {
        _cancel = callCancel;
    }


    public DeleteMyLabelDialog(Context context, String title, String notice, String delete, String ok) {
        super(context, R.style.AlertDialogStyle);
        setContentView(R.layout.view_delete_mylabeldialog);
        this.context = context;
        txtNotice = (TextView) findViewById(R.id.dialog_message);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        txtOk = (TextView) findViewById(R.id.ok);
        txtDelete = (TextView) findViewById(R.id.delete);
        txtNotice.setText(notice);
        tvTitle.setText(title);
        txtOk.setText(ok);
        txtDelete.setText(delete);
        initView();
    }


    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float density = dm.density;
        density = 1;
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.AnimBottom);
        findViewById(R.id.ok).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok: {
                _sure.onSureResult(DeleteMyLabelDialog.this, true);
                break;
            }

            case R.id.delete: {
                _cancel.onCancelResult(DeleteMyLabelDialog.this, true);
                break;
            }
            default:
                break;
        }
    }
}
