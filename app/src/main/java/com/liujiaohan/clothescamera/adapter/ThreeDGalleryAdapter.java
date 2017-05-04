package com.liujiaohan.clothescamera.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.R;
import com.liujiaohan.clothescamera.activity.FittingRoom;
import com.liujiaohan.clothescamera.activity.MainActivity;
import com.liujiaohan.clothescamera.util.ContextUtil;

/**
 * Created by Administrator on 2017-04-25.
 */

public class ThreeDGalleryAdapter extends PagerAdapter {
    private int[] clothesList;

    public ThreeDGalleryAdapter(int[] clothesList) {
        this.clothesList = clothesList;
    }

    @Override
    public int getCount() {
        return clothesList.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView cloth=new ImageView(ContextUtil.getContext());
        cloth.setImageResource(clothesList[position]);
        container.addView(cloth);
        return cloth;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
