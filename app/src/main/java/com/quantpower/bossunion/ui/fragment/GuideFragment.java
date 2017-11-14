package com.quantpower.bossunion.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quantpower.bossunion.R;
import com.quantpower.bossunion.widget.extend.CustomVideoView;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by ShuLin on 2017/7/15.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class GuideFragment extends Fragment {
    private CustomVideoView customVideoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customVideoView = new CustomVideoView(getContext());
        /**获取参数，根据不同的参数播放不同的视频**/
        int index = getArguments().getInt("index");
        Uri uri;
        if (index == 1) {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_0);
        } else if (index == 2) {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_2);
        } else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.spread);
        }
        /**播放视频**/
        customVideoView.playVideo(uri);
        return customVideoView;
    }

    /**
     * 记得在销毁的时候让播放的视频终止
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
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
