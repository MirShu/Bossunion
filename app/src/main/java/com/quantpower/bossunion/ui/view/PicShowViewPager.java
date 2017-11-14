package com.quantpower.bossunion.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by likai on 2017/5/11.
 * email: codingkai@163.com
 * 重写viewpager ,避免索引越界异常
 */

public class PicShowViewPager extends ViewPager {
    public PicShowViewPager(Context context) {
        super(context);
    }

    public PicShowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
