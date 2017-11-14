package com.quantpower.bossunion.adapter.gridview;

/**
 * Created by likai on 2017/5/11.
 * email: codingkai@163.com
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quantpower.bossunion.R;
import com.quantpower.bossunion.common.ImageLoaderEx;
import com.quantpower.bossunion.utils.UIUtils;

public class DynamicGridAdapter extends BaseAdapter {
    private String[] files;

    private LayoutInflater mLayoutInflater;

    public DynamicGridAdapter(String[] files, Context context) {
        this.files = files;
        mLayoutInflater = LayoutInflater.from(context);
        Log.e("______count_____",""+getCount());

    }

    @Override
    public int getCount() {
        return files == null ? 0 : files.length;
    }

    @Override
    public String getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_dynamic_grideview,
                    parent, false);

            viewHolder.imageView = (RoundedImageView) convertView
                    .findViewById(R.id.album_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        String url = getItem(position);
        if (getCount() == 1) {
            viewHolder.imageView.setLayoutParams(new android.widget.AbsListView.LayoutParams(UIUtils.dp2px(160), UIUtils.dp2px(160)));
        }

//        if (getCount() == 2 ||getCount() == 4) {
//            viewHolder.imageView.setLayoutParams(new android.widget.AbsListView.LayoutParams(200, 200));
//        }

//        ImageLoader.getInstance().displayImage(url, viewHolder.imageView);
        ImageLoader.getInstance().displayImage(url,viewHolder.imageView, ImageLoaderEx.getDisplayImageOptions());


        return convertView;
    }

    private static class MyGridViewHolder {
        RoundedImageView imageView;
    }
}
