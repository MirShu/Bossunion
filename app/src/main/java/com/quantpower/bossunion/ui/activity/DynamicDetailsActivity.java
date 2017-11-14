package com.quantpower.bossunion.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.gridview.DynamicGridAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.ChatMessageResult;
import com.quantpower.bossunion.model.ChatModel;
import com.quantpower.bossunion.model.CommentModel;
import com.quantpower.bossunion.model.DiscoveryList;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.extend.NoScrollGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/7/24.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class DynamicDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_praise)
    TextView tvPraise;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private String[] images;
    @BindView(R.id.gridView_disc)
    NoScrollGridView gridViewDisc;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerAdapter myOrderReycAdapter;
    private List<String> myOrderReycList;


    @Override
    public int getContentViewId() {
        return R.layout.activity_dynamic_details;

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.bindRecycleView();
        images = new String[]{
                "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC7HVQ54GI0096NOS.jpg",
                "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC7HVR54GI0096NOS.jpg",
                "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC7HVS54GI0096NOS.jpg"
        };
        //加载gridview7
        gridViewDisc.setAdapter(new DynamicGridAdapter(images, DynamicDetailsActivity.this));
        gridViewDisc.setOnItemClickListener((adapterView, view, position1, id) -> {
            Intent intent = new Intent(DynamicDetailsActivity.this, MyDynamcPicShowActivity.class);
            startActivity(intent);
            DynamicDetailsActivity.this.overridePendingTransition(R.anim.scale_in, R.anim.scale_in);

        });
    }

    private void bindRecycleView() {
        this.getData();
        this.myOrderReycAdapter = new RecyclerAdapter<String>(DynamicDetailsActivity.this, myOrderReycList,
                R.layout.item_reply) {
            @Override
            public void convert(RecyclerViewHolder helper,String item, int position) {
                String tvReply1 = MessageFormat.format("<font color=\"#6b9bcd\">萧十一郎：</font><font color=\"#666666\">{0}</font>", "社会流氓多，好自为之");
                helper.setText(R.id.tv_reply1, Html.fromHtml(tvReply1));
                String tvReply2 = MessageFormat.format("<font color=\"#6b9bcd\">@孤独>>小狼：</font><font color=\"#666666\">{0}</font>", "胸弟好文采啊，厉害了我的哥");
                helper.setText(R.id.tv_reply2, Html.fromHtml(tvReply2));
                String tvReply3 = MessageFormat.format("<font color=\"#6b9bcd\"> @柯亚德回复萧十一郎：</font><font color=\"#666666\">{0}</font>", "怎么样来一发，大刀饥渴难耐等候已久，随时切磋啊，放马过来试试");
                helper.setText(R.id.tv_reply3, Html.fromHtml(tvReply3));
                ImageView ivHead = helper.getView(R.id.iv_head);
                TextView tvName = helper.getView(R.id.tv_name);

                LinearLayout llReply = helper.getView(R.id.ll_reply);
                if (position == 0) {
                    ivHead.setImageResource(R.mipmap.icon_mation01);
                    tvName.setText("流失花样年华");
                    llReply.setVisibility(View.GONE);
                } else if (position == 1) {
                    ivHead.setImageResource(R.mipmap.icon_mation02);
                    tvName.setText("遗失美好");
                } else if (position == 2) {
                    ivHead.setImageResource(R.mipmap.icon_mation013);
                    tvName.setText("送走青春");
                    llReply.setVisibility(View.GONE);
                } else if (position == 3) {
                    ivHead.setImageResource(R.mipmap.icon_mation04);
                    tvName.setText("心不老");
                } else if (position == 4) {
                    llReply.setVisibility(View.GONE);
                    ivHead.setImageResource(R.mipmap.icon_mation05);
                    tvName.setText("痴心");
                }
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(DynamicDetailsActivity.this, 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.myOrderReycAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
//        this.myOrderReycAdapter.setOnItemClickListener((parent, position)
//                -> UIHelper.startActivity(MationNewsActivity.this, OthersInformationActivity.class));

    }

    private void getData() {
        myOrderReycList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            myOrderReycList.add("" + i);
        }

    }


    @OnClick({R.id.iv_back_icon, R.id.tv_praise, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_icon:
                finish();
                break;
            case R.id.tv_praise:
                break;
            case R.id.tv_share:
                break;
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
