package com.liujiaohan.clothescamera.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Administrator on 2017-04-23.
 */

public class ImageUtil {
    private static final String Tag="ImageUtil";
    /*
    rotate Image
     */
    public static Bitmap getRotateBitmap(Bitmap b,float rotateDegree){
        Matrix matrix =new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotateBitmap=Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(),matrix,false);
        return rotateBitmap;
    }
}
