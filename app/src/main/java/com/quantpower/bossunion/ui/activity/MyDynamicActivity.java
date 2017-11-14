/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.gridview.DynamicGridAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.extend.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

import static android.R.attr.maxLines;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * 历史动态
 */

public class MyDynamicActivity extends BaseActivity {

    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.recy_dynamic)
    RecyclerView recyDynamic;
    @BindView(R.id.tv_null_bg)
    TextView tvNullBg;

    private RecyclerAdapter dynamicAdapter;
    private List<String> dynamicList;
    private String[] images;


    @Override
    public int getContentViewId() {
        return R.layout.activity_my_dynamic;

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("历史动态");
        bindRecyclerView();
        tvNullBg.setText("大人，您还未发布任何动态");
        tvNullBg.setVisibility(View.GONE);
        recyDynamic.setVisibility(View.VISIBLE);


    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }

    public void bindRecyclerView() {
        getDate();
        this.dynamicAdapter = new RecyclerAdapter<String>(MyDynamicActivity.this, dynamicList, R.layout.item_my_dynamic) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvDevelop = helper.getView(R.id.tv_develop);
                TextView tvText = helper.getView(R.id.tv_text);
                NoScrollGridView gridView = helper.getView(R.id.gridView);
                RelativeLayout industryTag = helper.getView(R.id.rl_industry_tag);
                TextView industryLine = helper.getView(R.id.tv_industry_line);
                LinearLayout llDecelop = helper.getView(R.id.ll_develop);
                CheckBox cbDevelop = helper.getView(R.id.iv_develop);
                TextView tvDelete = helper.getView(R.id.tv_delete_bottonbar);
                industryLine.bringToFront();
                gridView.setVisibility(View.VISIBLE);
                if (position == 0) {
                    images = new String[]{

                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                    };
                }
                if (position == 1) {
                    images = new String[]{

                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                    };
                }
                if (position == 2) {
                    images = new String[]{

                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                    };
                }
                if (position == 3) {
                    images = new String[]{

                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                    };
                }
                if (position == 4) {
                    images = new String[]{

                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg",
                            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                            , "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg"
                    };
                }

                //加载gridview
                gridView.setAdapter(new DynamicGridAdapter(images, context));
                gridView.setOnItemClickListener((adapterView, view, position1, id) -> {
                    UIHelper.toastMessage(MyDynamicActivity.this, "position" + position1);
                    Intent intent = new Intent(MyDynamicActivity.this, MyDynamcPicShowActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_in);

                });
                llDecelop.setOnClickListener(view -> {
                    if (tvDevelop.getText().equals("展开")) {
                        tvDevelop.setText("收起");
                        tvText.setEllipsize(null);
                        tvText.setMaxLines(maxLines);
                        cbDevelop.setChecked(true);


                    } else {
                        tvDevelop.setText("展开");
                        tvText.setLines(3);
                        tvText.setEllipsize(TextUtils.TruncateAt.END);
                        cbDevelop.setChecked(false);

                    }
                });

                //点击删除

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dynamicList.remove(position);
                        dynamicAdapter.notifyItemChanged(position);
                    }
                });

            }
        };
        this.recyDynamic.setHasFixedSize(true);
        this.recyDynamic.setLayoutManager(new LinearLayoutManager(MyDynamicActivity.this, 1, false));
        this.recyDynamic.setAdapter(this.dynamicAdapter);
    }

    private void getDate() {
        dynamicList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            dynamicList.add("haha");

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
