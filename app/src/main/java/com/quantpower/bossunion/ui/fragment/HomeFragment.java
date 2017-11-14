/*
 * CopCopyright © 2016-2017
 * 上海量雷信息科技有限公司 版权所有 违者必究
 * Shanghai Quantpower Information Technology Co.,Ltd.
 * QQ：2880977679
 * E-mail： info@quant-power
 */

package com.quantpower.bossunion.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.adapter.recycler.RecyclerAdapter;
import com.quantpower.bossunion.adapter.recycler.RecyclerViewHolder;
import com.quantpower.bossunion.adapter.gridview.DynamicGridAdapter;
import com.quantpower.bossunion.base.BaseFragment;
import com.quantpower.bossunion.constant.SysStatusCode;
import com.quantpower.bossunion.constant.URLS;
import com.quantpower.bossunion.model.DiscoveryList;
import com.quantpower.bossunion.model.InterestedList;
import com.quantpower.bossunion.model.MessageResult;
import com.quantpower.bossunion.ui.activity.DynamicDetailsActivity;
import com.quantpower.bossunion.ui.activity.MyDynamcPicShowActivity;
import com.quantpower.bossunion.ui.activity.OthersInformationActivity;
import com.quantpower.bossunion.ui.activity.SearchActivity;
import com.quantpower.bossunion.ui.activity.TopicsFocusedActivity;
import com.quantpower.bossunion.utils.Constants;
import com.quantpower.bossunion.utils.UIHelper;
import com.quantpower.bossunion.widget.dialog.LodingDialog;
import com.quantpower.bossunion.widget.extend.NoScrollGridView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

import static android.R.attr.maxLines;

/**
 * Created by ShuLin on 2017/4/26.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 * Bnner 非常灵活第三方说明文档地址
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.image_scan)
    ImageView imageScan;
    @BindView(R.id.image_plus)
    ImageView imagePlus;

    @BindView(R.id.recyclerView)
    RecyclerView recyDynamic;
    @BindView(R.id.gridView_disc)
    NoScrollGridView gridViewDisc;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private RecyclerAdapter dynamicAdapter;
    private List<String> dynamicList;


    @BindView(R.id.recv_interested)
    RecyclerView recvInterested;
    private RecyclerAdapter<DiscoveryList.ListBean> interestedAdapter;
    private List<DiscoveryList.ListBean> interestedList = new ArrayList<>();


    @BindView(R.id.recv_attention)
    RecyclerView recyAttention;
    private RecyclerAdapter attentiondAdapter;
    private List<String> attentionList;


    @BindView(R.id.recv_discovery)
    RecyclerView recyDiscovery;
    private List<DiscoveryList.ListBean> discoveryList = new ArrayList<>();
    private RecyclerAdapter<DiscoveryList.ListBean> discoveryAdapter;

    LodingDialog lodingDialog;
    private String[] images;

    public static HomeFragment newInstance(String s) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        lodingDialog = new LodingDialog(getActivity(), "加载中...");
        lodingDialog.show();
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);
        this.bindRecycleView();
        gridViewDisc.setVisibility(View.VISIBLE);
        images = new String[]{
                "https://ws1.sinaimg.cn/large/610dc034ly1fgbbp94y9zj20u011idkf.jpg",
                "https://ws1.sinaimg.cn/large/610dc034ly1fgllsthvu1j20u011in1p.jpg",
                "https://ws1.sinaimg.cn/large/610dc034ly1fgdmpxi7erj20qy0qyjtr.jpg"
        };
        //加载gridview7
        gridViewDisc.setAdapter(new DynamicGridAdapter(images, context));
        gridViewDisc.setOnItemClickListener((adapterView, view, position1, id) -> {
            Intent intent = new Intent(getActivity(), MyDynamcPicShowActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.scale_in, R.anim.scale_in);

        });
        refreshView();

    }


    private void refreshView() {
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefresh.setProgressViewOffset(true, 0, 100);
        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefresh.setColorSchemeResources(
                android.R.color.black,
                android.R.color.darker_gray,
                android.R.color.primary_text_dark,
                android.R.color.secondary_text_dark_nodisable);
        swipeRefresh.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
                    swipeRefresh.setRefreshing(false);
                    UIHelper.toastMessage(getActivity(), "刷新成功");
                }, 1500)
        );


    }

    private void bindRecycleView() {

        /**
         * 头部RecyclerView
         */
        getDate();
        this.dynamicAdapter = new RecyclerAdapter<String>(getActivity(), dynamicList, R.layout.item_home_reyc) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                LinearLayout llTopic = helper.getView(R.id.ll_topic);
                TextView tvDevelop = helper.getView(R.id.tv_develop);
                TextView tvText = helper.getView(R.id.tv_text);
                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvPosition = helper.getView(R.id.tv_position);
                ImageView ivHead = helper.getView(R.id.iv_head);
                NoScrollGridView gridView = helper.getView(R.id.gridView);
                TextView industryLine = helper.getView(R.id.tv_industry_line);
                LinearLayout llDecelop = helper.getView(R.id.ll_develop);
                CheckBox cbDevelop = helper.getView(R.id.iv_develop);
                TextView tvDelete = helper.getView(R.id.tv_delete_bottonbar);
                llTopic.setOnClickListener(v -> UIHelper.startActivity(getActivity(), TopicsFocusedActivity.class));
                industryLine.bringToFront();
                gridView.setVisibility(View.VISIBLE);
                if (position == 0) {
                    llTopic.setVisibility(View.VISIBLE);
                    tvText.setText(R.string.tv_bank);
                    tvName.setText("张启山");
                    ivHead.setImageResource(R.mipmap.icon_hm_po01);
                    images = new String[]{
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC70EM54GI0096NOS.jpg",
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC70EN54GI0096NOS.jpg",
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC70EO54GI0096NOS.jpg"
                    };
                }
                if (position == 1) {
                    tvText.setText(R.string.tv_medical_industry);
                    tvName.setText("魏亮");
                    ivHead.setImageResource(R.mipmap.icon_hm_po002);
                    tvPosition.setText("国家卫生和计划生育委员会主任、党组书记");
                    images = new String[]{
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IL17O254GI0096NOS.jpg",
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IL17O354GI0096NOS.jpg"
                    };
                }
                if (position == 2) {
                    tvText.setText(R.string.tv_Industry);
                    tvName.setText("李庆梅");
                    ivHead.setImageResource(R.mipmap.icon_hm_po04);
                    tvPosition.setText("中华人民共和国工业和信息化部委员");
                    images = new String[]{
                    };
                }

//                //加载gridview
                gridView.setAdapter(new DynamicGridAdapter(images, context));
                gridView.setOnItemClickListener((adapterView, view, position1, id) -> {
                    Intent intent = new Intent(getActivity(), MyDynamcPicShowActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.scale_in, R.anim.scale_in);

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
                tvDelete.setOnClickListener(view -> {
                    dynamicList.remove(position);
                    dynamicAdapter.notifyItemChanged(position);
                });

            }
        };
        this.recyDynamic.setHasFixedSize(true);
        this.recyDynamic.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.recyDynamic.setAdapter(this.dynamicAdapter);
        this.dynamicAdapter.setOnItemClickListener((parent, position) -> UIHelper.startActivity(getActivity(), DynamicDetailsActivity.class));

        /**
         *发现横向RecyclerView
         */
        getDisData();
        this.discoveryAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(getActivity(), discoveryList,
                R.layout.item_discovery) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {
                String location = item.getGps();
                TextView tvAttTite = helper.getView(R.id.tv_attention_tite);
                TextView gps = helper.getView(R.id.tv_location);
                helper.setText(R.id.tv_dis_name, item.getFamilyName());
                helper.setImageUrl(R.id.iv_head, item.getSmallpic());
                helper.setText(R.id.tv_context, item.getMyname());
                if (location.equals("来自喵星")) {
                    gps.setText("来自波士界家族");
                } else {
                    helper.setText(R.id.tv_location, item.getGps());
                }
                if (item.getStarlevel() == 5) {
                    tvAttTite.setText("已关注");
                    tvAttTite.setTextColor(ContextCompat.getColor(context, R.color.white));
                    tvAttTite.setBackgroundResource(R.drawable.shape_att_on);
                }
            }
        };
        this.recyDiscovery.setHasFixedSize(true);
        this.recyDiscovery.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                LinearLayoutManager.HORIZONTAL, false));
        this.recyDiscovery.setAdapter(this.discoveryAdapter);
        this.discoveryAdapter.setOnItemClickListener((parent, position) -> {
            String allnum = String.valueOf(discoveryList.get(position).getAllnum());
            String roomid = String.valueOf(discoveryList.get(position).getRoomid());
            Bundle bundle = new Bundle();
            bundle.putString(OthersInformationActivity.SMALLPIC_URL, discoveryList.get(position).getSmallpic());
            bundle.putString(OthersInformationActivity.BIGPIC_URL, discoveryList.get(position).getBigpic());
            bundle.putString(OthersInformationActivity.MYNAME, discoveryList.get(position).getMyname());
            bundle.putString(OthersInformationActivity.ALLNUM, allnum);
            bundle.putString(OthersInformationActivity.ROOMID, roomid);
            UIHelper.startActivity(getActivity(), OthersInformationActivity.class, bundle);
        });

        /**
         *感兴趣横向RecyclerView
         */
        getInData();
        this.interestedAdapter = new RecyclerAdapter<DiscoveryList.ListBean>(getActivity(), interestedList,
                R.layout.item_interested) {
            @Override
            public void convert(RecyclerViewHolder helper, DiscoveryList.ListBean item, int position) {

                helper.setText(R.id.tv_context, item.getMyname());
                helper.setImageUrl(R.id.iv_head, item.getSmallpic());
                helper.setText(R.id.tv_state, item.getFamilyName());
                TextView tvState = helper.getView(R.id.tv_state);
                TextView tvIntersTite = helper.getView(R.id.tv_inters_tite);
                TextView tvContext = helper.getView(R.id.tv_context);
                if (position == 0) {
                    tvState.setText("饮食文化");
                    tvIntersTite.setText("黄埔点冰咖啡店项目");
                    tvContext.setText("坐落黄埔中心黄金地带咖啡休闲与一体项目。");
                } else if (position == 1) {
                    tvState.setText("娱乐");
                    tvIntersTite.setText("欧亚广场游乐园");
                    tvContext.setText("儿童，购物，休闲广场，儿童游玩小天地。");
                } else if (position == 2) {
                    tvState.setText("影视");
                    tvIntersTite.setText("4D max动感影视");
                    tvContext.setText("让你感受前所未有的身临其境影视效果，high到尖叫，等着你的到来");
                } else if (position == 3) {
                    tvState.setText("休闲");
                    tvIntersTite.setText("飞行欧米伽美食广场");
                    tvContext.setText("欧式风格飞行欧米咖美食广场，感受到异国风情的同时，也是周末休闲娱乐，必去之地");
                } else if (position == 4) {
                    tvState.setText("技术行业");
                    tvIntersTite.setText("飞亚无人机技术");
                    tvContext.setText("让技术不再遥远，技术改变生活，技术改变世界");
                }
            }
        };

        this.recvInterested.setHasFixedSize(true);
        this.recvInterested.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        this.recvInterested.setAdapter(this.interestedAdapter);
        this.interestedAdapter.setOnItemClickListener((parent, position) -> {
            String allnum = String.valueOf(interestedList.get(position).getAllnum());
            String roomid = String.valueOf(interestedList.get(position).getRoomid());
            Bundle bundle = new Bundle();
            bundle.putString(OthersInformationActivity.SMALLPIC_URL, interestedList.get(position).getSmallpic());
            bundle.putString(OthersInformationActivity.BIGPIC_URL, interestedList.get(position).getBigpic());
            bundle.putString(OthersInformationActivity.MYNAME, interestedList.get(position).getMyname());
            bundle.putString(OthersInformationActivity.ALLNUM, allnum);
            bundle.putString(OthersInformationActivity.ROOMID, roomid);
            UIHelper.startActivity(getActivity(), OthersInformationActivity.class, bundle);
        });


        /**
         *底部RecyclerView
         */
        getAttData();
        this.attentiondAdapter = new RecyclerAdapter<String>(getActivity(), attentionList,
                R.layout.item_attention) {
            @Override
            public void convert(RecyclerViewHolder helper, String item, int position) {
                TextView tvDelete = helper.getView(R.id.tv_delete_bottonbar);
                TextView tvDevelop = helper.getView(R.id.tv_develop);
                ImageView ivHiLogo = helper.getView(R.id.iv_hi_logo);
                TextView tvHiLogo = helper.getView(R.id.tv_hi_logo);
                TextView tvText = helper.getView(R.id.tv_text);
                LinearLayout llHiLogo = helper.getView(R.id.ll_hi_logo);
                TextView tvAttentionTite = helper.getView(R.id.tv_attention_tite);
                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvShareTite = helper.getView(R.id.tv_share_tite);
                TextView tvPosition = helper.getView(R.id.tv_position);
                ImageView ivHead = helper.getView(R.id.iv_atten_head);
                NoScrollGridView gridViewAtt = helper.getView(R.id.gridView);
                TextView industryLine = helper.getView(R.id.tv_industry_line);
                LinearLayout llDecelop = helper.getView(R.id.ll_develop);
                CheckBox cbDevelop = helper.getView(R.id.iv_develop);
                industryLine.bringToFront();
                gridViewAtt.setVisibility(View.VISIBLE);
                if (position == 0) {
                    industryLine.setVisibility(View.GONE);
                    tvText.setVisibility(View.GONE);
                    llDecelop.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.GONE);
                    tvName.setText("朱玉军");
                    tvPosition.setText("SOIA中国的董事长");
                    ivHead.setImageResource(R.mipmap.icon_zy);
                    images = new String[]{
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IHB9CF54GI0096NOS.jpg",
                    };
                }
                if (position == 1) {
                    tvDelete.setVisibility(View.GONE);
                    tvAttentionTite.setVisibility(View.GONE);
                    tvName.setText("尤敏娇");
                    tvText.setText(R.string.tv_pos02);
                    tvPosition.setText("上海君高信息科技有限公司资深HR");
                    ivHead.setImageResource(R.mipmap.icon_positon02);
                    images = new String[]{
                    };
                }
                if (position == 2) {
                    tvName.setText("潘欣");
                    tvPosition.setText("杭州星星信息科技有限公司总经理");
                    tvDelete.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvText.setText(R.string.tv_px);
                    ivHead.setImageResource(R.mipmap.icon_positon03);
                }
                if (position == 3) {
                    tvName.setText("吴琳");
                    tvPosition.setText("资深融资评估师");
                    tvDelete.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvText.setText(R.string.tv_wl);
                    ivHiLogo.setImageResource(R.mipmap.icon_car);
                    tvHiLogo.setText("梅赛德斯-奔驰131年创新激情永不灭，作为矢志创新的引领者，我们从未停下脚步，为心中所向，驰之以恒。");
                    ivHead.setImageResource(R.mipmap.icon_positon04);
                }
                if (position == 4) {
                    tvName.setText("龙志斌");
                    tvPosition.setText("上海小猪科技人力资源师");
                    tvDelete.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvText.setText(R.string.tv_xz);
                    ivHiLogo.setImageResource(R.mipmap.icon_kj);
                    tvHiLogo.setText("24小时滚动报道IT业界,电信、互联网及大众科技新闻,最及时权威的产业及事件报道平台,手机、数码、笔记本及软件下载一网打尽。");
                    ivHead.setImageResource(R.mipmap.icon_positon05);
                    images = new String[]{
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC98T254GI0096NOS.jpg",
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC98T354GI0096NOS.jpg",
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IC98T454GI0096NOS.jpg"
                    };
                }
                if (position == 5) {
                    tvName.setText("李丽");
                    tvPosition.setText("百度竞价资深首席评估师");
                    ivHead.setImageResource(R.mipmap.icon_positon06);
                    industryLine.setVisibility(View.GONE);
                    tvText.setVisibility(View.GONE);
                    llDecelop.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    ivHiLogo.setImageResource(R.mipmap.icon_jingj);
                    tvHiLogo.setText("通过市场运营机构（或电力交易中心）组织交易的卖方或买方参与市场投标，以竞争方式确定交易量及其价格的过程。在电力市场中，通常用bidder表示买方投标者，用offer表示卖方投标者。");
                    images = new String[]{
                            "http://pic-bucket.nosdn.127.net/photo/0096/2017-11-06/D2IHB9CD54GI0096NOS.jpg",
                    };
                }
                if (position == 6) {
                    tvName.setText("车浩建");
                    tvPosition.setText("上海萌物网市场部总监");
                    tvShareTite.setVisibility(View.VISIBLE);
                    industryLine.setVisibility(View.GONE);
                    tvText.setVisibility(View.GONE);
                    llDecelop.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvAttentionTite.setVisibility(View.GONE);
                    ivHiLogo.setImageResource(R.mipmap.icon_shj);
                    tvHiLogo.setText("大洗牌之下，出货量不到1亿的手机厂商该如何生存?今年手机更不好做了，厂商都在互相抢用户。”对于目前手机市场的竞争，有厂商从业人员表示。");
                    ivHead.setImageResource(R.mipmap.icon_positon07);
                    images = new String[]{
                    };
                }
                if (position == 7) {
                    tvName.setText("朱勇");
                    tvPosition.setText("化妆网首席策划师");
                    ivHead.setImageResource(R.mipmap.icon_positon08);
                    tvShareTite.setVisibility(View.VISIBLE);
                    industryLine.setVisibility(View.GONE);
                    tvText.setVisibility(View.GONE);
                    llDecelop.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvAttentionTite.setVisibility(View.GONE);
                    industryLine.setVisibility(View.GONE);
                    ivHiLogo.setImageResource(R.mipmap.icon_ym);
                    tvHiLogo.setText("美国联邦贸易委员会(FTC)和纽约州总检察长，日前联合起诉该产品制造商昆西生物科技公司(Quincy Bioscience)涉嫌虚假广告，欺骗消费者。但记者发现，在淘宝上搜索关键词“Prevagen”，目前仍有不少店铺在出售号称从美国代购的该品牌“健脑胶囊”，一瓶(30粒)售价在500元至1000元人民币不等。");
                    images = new String[]{
                    };
                }
                if (position == 8) {
                    tvName.setText("裴星星");
                    tvText.setText(R.string.tv_jj);
                    tvPosition.setText("好未来点点科技有限公司总经理");
                    ivHead.setImageResource(R.mipmap.icon_positon09);
                    industryLine.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    ivHiLogo.setImageResource(R.mipmap.icon_keji);
                    tvHiLogo.setText("北京时间今天凌晨，微软在洛杉矶E3大展上发布了号称“史上最强主机”的新一代Xbox主机--Xbox One X，发布会现场展示了即将登陆新Xbox的42款游戏的宣传片，吊足了玩家的胃口，让人感到有些意外是，这次微软没有提及基于新Xbox平台的VR计划。");
                }
                if (position == 9) {
                    tvName.setText("郝毅建");
                    tvPosition.setText("车网中国执行董事长");
                    ivHead.setImageResource(R.mipmap.icon_positon04);
                    tvText.setText("先锋科技股票一路飙升");
                    industryLine.setVisibility(View.GONE);
                    llHiLogo.setVisibility(View.VISIBLE);
                    llDecelop.setVisibility(View.GONE);
                    ivHiLogo.setImageResource(R.mipmap.icon_gp);
                    tvHiLogo.setText("股票普及,股市风险难以把控,试试外汇100美金开始交易,模拟交易学起,简单易懂,立即注册免费模拟帐户!");
                }
//                //加载gridview
                gridViewAtt.setAdapter(new DynamicGridAdapter(images, context));
                gridViewAtt.setOnItemClickListener((adapterView, view, position1, id) -> {
                    Intent intent = new Intent(getActivity(), MyDynamcPicShowActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.scale_in, R.anim.scale_in);

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
            }
        };

        this.recyAttention.setHasFixedSize(true);
        this.recyAttention.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                LinearLayoutManager.VERTICAL, false));
        this.recyAttention.setAdapter(this.attentiondAdapter);
        attentiondAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View parent, int position) {
                UIHelper.startActivity(getActivity(), DynamicDetailsActivity.class);
            }
        });
    }


    private void getAttData() {
        attentionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            attentionList.add("" + i);
        }

    }

    private void getDisData() {
        RequestParams params = new RequestParams(URLS.DISCOVERY_LIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<DiscoveryList.ListBean> list;
                DiscoveryList model = JSON.parseObject(message.getData(), DiscoveryList.class);
                model.getList();
                list = model.getList();
                discoveryList.addAll(list);
                discoveryAdapter.notifyDataSetChanged();


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
     * 获得感兴趣的
     */
    private void getInData() {
        RequestParams params = new RequestParams(URLS.INTERESTED_LIST);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MessageResult message = MessageResult.parse(result);
                List<DiscoveryList.ListBean> list;
                DiscoveryList model = JSON.parseObject(message.getData(), DiscoveryList.class);
                model.getList();
                list = model.getList();
                interestedList.addAll(list);
                interestedAdapter.notifyDataSetChanged();


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


    private void getDate() {
        dynamicList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dynamicList.add("haha");

        }
    }

    @OnClick({R.id.image_scan, R.id.image_plus, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_scan:
                UIHelper.toastMessage(getContext(), "未开放");
                break;
            case R.id.image_plus:
                UIHelper.toastMessage(getContext(), "未开放");
                break;
            case R.id.tv_search:
                UIHelper.startActivity(getActivity(), SearchActivity.class);
                break;
        }
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
