/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.FlowLayoutManager;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.widget.dialog.AddMyLabelDialog;
import com.quantpower.bossunion.widget.dialog.DeleteMyLabelDialog;
import com.quantpower.bossunion.widget.extend.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/5/9.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * 定制我的标签
 */

public class DTMyLabelActivity extends BaseActivity {
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.rv_provide)
    RecyclerView recyProvide;
    @BindView(R.id.rv_get)
    RecyclerView recyGet;

    private final int TEXTCOLOR = 0x5f666666;
    private final int TEXTADDCOLOR = 0xff666666;
    private RecyclerAdapter provideAdapter;
    private RecyclerAdapter getAdapter;
    private List<String> provideList = new ArrayList<>();
    private List<String> getList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_dt_label;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        tvMainTitle.setText("定制我的标签");
        bindRecycleView();


    }

    @OnClick({R.id.image_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.tv_save:
                ToastUtil.showShareToast(DTMyLabelActivity.this, R.mipmap.icon_success, "保存成功");
                break;
        }
    }


    private void bindRecycleView() {
        this.getProvideData();
        this.getMygetData();

        /**
         * 能提供recycleview的adapter
         */
        this.provideAdapter = new RecyclerAdapter<String>(DTMyLabelActivity.this, provideList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                TextView mTitleUp = helper.getView(R.id.tv_title_up);
                helper.setText(R.id.tv_title, provideList.get(position));
                helper.setText(R.id.tv_title_on, provideList.get(position));
                helper.setText(R.id.tv_title_up, provideList.get(position));
                mTitle.setTextColor(TEXTADDCOLOR);

                /**
                 * 定义3种recycleview条目,根据条目位置显示和隐藏
                 * 各种条目对应各种点击事件
                 */
                if (position == 0) {
                    if (provideList.size() >= 4) {
                        mTitle.setVisibility(View.GONE);
                        mTitleOn.setVisibility(View.VISIBLE);
                        mTitleUp.setVisibility(View.GONE);
                        mTitleOn.getBackground().setAlpha(102);
                        mTitleOn.setTextColor(TEXTCOLOR);
                    } else {
                        mTitle.setVisibility(View.VISIBLE);
                        mTitleOn.setVisibility(View.GONE);
                        mTitleUp.setVisibility(View.GONE);
                    }
                } else {
                    mTitleUp.setVisibility(View.VISIBLE);
                    mTitleOn.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);
                }

                /**
                 * 点击除去0位置的条目,可以删除该条目
                 */

                mTitleUp.setOnClickListener(view -> {
                    DeleteMyLabelDialog noticeDialogAbutton = new DeleteMyLabelDialog(DTMyLabelActivity.this,"",
                            "确定要删除\""+provideList.get(position)+"\"标签吗?", "取消",
                            "确定");
                    noticeDialogAbutton.setSure((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                            provideList.remove(position);
                            provideAdapter.notifyDataSetChanged();
                        }
                    });
                    noticeDialogAbutton.setCancel((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                        }
                    });
                    noticeDialogAbutton.show();
                });


                /**
                 * 点击0位置的条目,可以添加条目
                 */
                mTitle.setOnClickListener(view -> {
                    AddMyLabelDialog addMyLabelDialog = new AddMyLabelDialog(DTMyLabelActivity.this,"","","取消","确定");
                    addMyLabelDialog.setCancel((dialog, flag) -> addMyLabelDialog.dismiss());

                    addMyLabelDialog.setSure((dialog, flag) -> {
                        String addItem = String.valueOf(dialog.getEdittext().getText());
                        addMyLabelDialog.dismiss();
                        provideList.add(addItem);
                        provideAdapter.notifyDataSetChanged();

                    });
                    addMyLabelDialog.show();
                });


            }
        };


        /**
         * 想得到recycleview的adapter
         */
        this.getAdapter = new RecyclerAdapter<String>(DTMyLabelActivity.this, getList,
                R.layout.item_industry) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView mTitle = helper.getView(R.id.tv_title);
                TextView mTitleOn = helper.getView(R.id.tv_title_on);
                TextView mTitleUp = helper.getView(R.id.tv_title_up);
                helper.setText(R.id.tv_title, getList.get(position));
                helper.setText(R.id.tv_title_on, getList.get(position));
                helper.setText(R.id.tv_title_up, getList.get(position));
                mTitle.setTextColor(TEXTADDCOLOR);

                /**
                 * 定义3种recycleview条目,根据条目位置显示和隐藏
                 * 各种条目对应各种点击事件
                 */
                if (position == 0) {
                    if (getList.size() >= 4) {
                        mTitle.setVisibility(View.GONE);
                        mTitleOn.setVisibility(View.VISIBLE);
                        mTitleUp.setVisibility(View.GONE);
                        mTitleOn.getBackground().setAlpha(102);
                        mTitleOn.setTextColor(TEXTCOLOR);
                    } else {
                        mTitle.setVisibility(View.VISIBLE);
                        mTitleOn.setVisibility(View.GONE);
                        mTitleUp.setVisibility(View.GONE);
                    }
                } else {
                    mTitleUp.setVisibility(View.VISIBLE);
                    mTitleOn.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);
                }

                /**
                 * 点击除去0位置的条目,可以删除该条目
                 */

                mTitleUp.setOnClickListener(view -> {
                    DeleteMyLabelDialog noticeDialogAbutton = new DeleteMyLabelDialog(DTMyLabelActivity.this,"",
                            "确定要删除\""+getList.get(position)+"\"标签吗?", "取消",
                            "确定");
                    noticeDialogAbutton.setSure((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                            getList.remove(position);
                            getAdapter.notifyDataSetChanged();
                        }
                    });
                    noticeDialogAbutton.setCancel((dialog, flag) -> {
                        if (flag) {
                            dialog.dismiss();
                        }
                    });
                    noticeDialogAbutton.show();
                });


                /**
                 * 点击0位置的条目,可以添加条目
                 */
                mTitle.setOnClickListener(view -> {
                    AddMyLabelDialog addMyLabelDialog = new AddMyLabelDialog(DTMyLabelActivity.this,"","","取消","确定");
                    addMyLabelDialog.setCancel((dialog, flag) -> {
                        addMyLabelDialog.dismiss();
                    });

                    addMyLabelDialog.setSure((dialog, flag) -> {
                        String addItem = String.valueOf(dialog.getEdittext().getText());
                        addMyLabelDialog.dismiss();
                        getList.add(addItem);
                        getAdapter.notifyDataSetChanged();

                    });
                    addMyLabelDialog.show();
                });
            }
        };

        this.recyProvide.setHasFixedSize(true);
        this.recyProvide.setLayoutManager(new FlowLayoutManager());
        this.recyProvide.setAdapter(this.provideAdapter);

        this.recyGet.setHasFixedSize(true);
        this.recyGet.setLayoutManager(new FlowLayoutManager());
        this.recyGet.setAdapter(this.getAdapter);
    }

    //获取能提供的数据集合
    private void getProvideData() {
        provideList.add("+添加");
        provideList.add("我要苍井空");
        provideList.add("我要武藤兰");
        provideList.add("我要加藤鹰");
    }

    //获取想得到的数据集合
    private void getMygetData() {
        getList.add("+添加");
        getList.add("苍井空是我");
        getList.add("武藤兰是我");
        getList.add("加藤鹰是我");
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
