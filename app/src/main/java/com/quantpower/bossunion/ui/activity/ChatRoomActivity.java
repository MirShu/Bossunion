package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.ChatMessageResult;
import com.quantpower.bossunion.model.ChatModel;
import com.quantpower.bossunion.model.DiscoveryList;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.utils.ImageLoaderEx;
import com.quantpower.bossunion.widget.extend.CircleImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/7/14.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class
ChatRoomActivity extends BaseActivity {
    public static final String MYNAME = "name";
    public static final String SMALLPICURL = "smallpicurl";
    public static final String BIGPICURL = "bigpicurl";
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.chat_bg)
    ImageView chatBg;
    private String name;
    private String smallpicurl;
    private String bigpicurl;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_showmessage)
    TextView tvShowMessage;
    private List<DiscoveryList.ListBean> chatReycList = new ArrayList<>();
    private RecyclerAdapter<DiscoveryList.ListBean> chatReycAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_chatroom;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.name = getIntent().getStringExtra(MYNAME);
        this.smallpicurl = getIntent().getStringExtra(SMALLPICURL);
        this.bigpicurl = getIntent().getStringExtra(BIGPICURL);
        tvMainTitle.setText(name);
        ImageLoader.getInstance().displayImage(smallpicurl, this.ivHead, ImageLoaderEx.getDefaultDisplayImageOptions());
        ImageLoader.getInstance().displayImage(bigpicurl, this.chatBg, ImageLoaderEx.getDefaultDisplayImageOptions());
        this.bindRecycleView();

    }

    private void bindRecycleView() {
        this.getData();
        this.getMessage();

        this.chatReycAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(ChatRoomActivity.this, chatReycList,
                R.layout.item_chat_reply) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {
                CircleImageView circleImageView = helper.getView(R.id.iv_head);
                ImageLoader.getInstance().displayImage(smallpicurl, circleImageView, ImageLoaderEx.getDefaultDisplayImageOptions());
            }
        };

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setNestedScrollingEnabled(false);
        this.recyclerView.setLayoutManager(new GridLayoutManager(ChatRoomActivity.this, 1, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.chatReycAdapter);
    }

    private void getData() {
        RequestParams params = new RequestParams(URLS.CHAT_LIST1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<DiscoveryList.ListBean> list;
                DiscoveryList model = JSON.parseObject(message.getData(), DiscoveryList.class);
                model.getList();
                list = model.getList();
                chatReycList.addAll(list);
                chatReycAdapter.notifyDataSetChanged();
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

    private void getMessage() {
        RequestParams params = new RequestParams(URLS.CHAT_LIST);
        params.addBodyParameter("info", "你是男的还是女的");
        params.addBodyParameter("dtype", "");
        params.addBodyParameter("loc", "");
        params.addBodyParameter("userid", "");
        params.addBodyParameter("key", "b82ecd8c29f5dfa1839d70f07e9489f1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ChatMessageResult chatMessage = ChatMessageResult.parse(result);
                ChatModel chatModel = JSON.parseObject(chatMessage.getResult(), ChatModel.class);
                String text = chatModel.getText();
                tvShowMessage.setText(text + "有什么需要服务的吗？奴家很乐意效劳！！");
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

    @OnClick(R.id.iv_back_icon)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_icon:
                finish();
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
