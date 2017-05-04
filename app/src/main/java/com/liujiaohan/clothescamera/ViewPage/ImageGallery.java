package com.liujiaohan.clothescamera.ViewPage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017-04-26.
 */

public class ImageGallery extends ViewPager {
    private boolean scrollable=true;
    public ImageGallery(Context context) {
        super(context);
    }

    public ImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(Boolean scrollable){
        this.scrollable=scrollable;
    }
    @Override
    public void scrollTo(int x,int y){
        if (scrollable){
            super.scrollTo(x,y);
        }
    }
}
