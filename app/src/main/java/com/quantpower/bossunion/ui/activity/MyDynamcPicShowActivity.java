package com.quantpower.bossunion.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.base.BaseActivity;
import com.quantpower.bossunion.ui.view.PicShowViewPager;
import com.quantpower.bossunion.utils.ImageLoaderEx;
import com.quantpower.bossunion.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class MyDynamcPicShowActivity extends BaseActivity {

    @BindView(R.id.vp)
    PicShowViewPager vp;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;

    private ViewPagerAdapter pageAdapter;
    private List<View> views = new ArrayList<>();
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(10, 10);
    private int position;


    // 图片缓存 默认 等
//    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
//            .showImageForEmptyUri(R.mipmap.zanwutupian)
//            .showImageOnFail(R.mipmap.zanwutupian).cacheInMemory(true).cacheOnDisk(true)
//            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
//            .bitmapConfig(Bitmap.Config.RGB_565).build();

    private String[] imageInfos = new String[]{
            "http://ww3.sinaimg.cn/large/610dc034gw1fay98gt0ocj20u011hn24.jpg",
            "http://ww2.sinaimg.cn/large/610dc034jw1fak99uh554j20u00u0n09.jpg",
            "http://ww4.sinaimg.cn/large/610dc034gw1fac4t2zhwsj20sg0izahf.jpg",
            "http://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg",
            "http://ww1.sinaimg.cn/large/610dc034jw1faaywuvd20j20u011gdli.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1fa7jol4pgvj20u00u0q51.jpg",
            "http://ww4.sinaimg.cn/large/610dc034gw1fa0ppsw0a7j20u00u0thp.jpg",
            "http://ww2.sinaimg.cn/large/610dc034gw1f9zjk8iaz2j20u011hgsc.jpg",
            "http://ww2.sinaimg.cn/large/610dc034jw1f9us52puzsj20u00u0jtd.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9tmhxq87lj20u011htae.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9rc3qcfm1j20u011hmyk.jpg",
            "http://liveimg.9158.com/pic/avator/2017-06/14/10/20170614102241_61267740_640.png",
            "http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg",
            "http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-25-13651793_897557617014845_571817176_n.jpg"
    };

    private LayoutAnimationController lac;


//    public MyDynamcPicShowActivity(Context context, int themeResId) {
//        this.context = context;
//    }


    //    public MyDynamcPicShowActivity(Context context, String[] imageInfos, int position) {
//        this(context, R.style.Pic_Dialog);
//        this.imageInfos = imageInfos;
//        this.position = position;
//    }
//
//
    @Override
    public int getContentViewId() {
        return R.layout.activity_my_dynamc_pic_show;


    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initMyPageAdapter();
        tvMainTitle.setText("图片预览");
        vp.setCurrentItem(1);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus2);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal2);
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }


    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new ViewPagerAdapter();
            if (vp != null) {
                vp.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        if (imageInfos.length == 1) {
            ll_point.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < imageInfos.length; i++) {
                View view = new View(this);
                paramsL.setMargins(dip2px(view, 5), dip2px(view, 2), 0, dip2px(view, 5));
                view.setLayoutParams(paramsL);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_focus2);
                } else {
                    view.setBackgroundResource(R.drawable.point_normal2);
                }

                views.add(view);
                ll_point.addView(view);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageInfos.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(MyDynamcPicShowActivity.this, R.layout.item_pic_show, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.pic_pv);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    UIHelper.toastMessage(MyDynamcPicShowActivity.this, "长按事件");
                    return false;
                }
            });

            ImageLoader.getInstance().displayImage(imageInfos[position], photoView, ImageLoaderEx.getDisplayImageOptions());
//            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                @Override
//                public void onPhotoTap(View view, float x, float y) {
//                    dismiss();
//                }
//            });
            ((ViewPager) container).addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    private int dip2px(View context, float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
