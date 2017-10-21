package com.liujiaohan.clothescamera.ViewPage;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2017-04-25.
 */

public class ThreeDTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE=0.85f;
    private static final float MIN_ALPHA=0.5f;
    private static final float MAX_ROTATION=30;
    @Override
    public void transformPage(View page, float position) {
        float scale=Math.max(MIN_SCALE,1-Math.abs(position));
        float rotation=Math.min(30*Math.abs(position),MAX_ROTATION);
        if (position<-1){

        }
        else if (position<0){
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setRotationY(rotation);
        }
        else if (position>0){
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setRotationY(-rotation);
        }
    }
}
