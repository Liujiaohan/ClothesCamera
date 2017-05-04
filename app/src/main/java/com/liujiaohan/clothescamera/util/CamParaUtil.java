package com.liujiaohan.clothescamera.util;


import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017-04-23.
 */

public class CamParaUtil {
    private static final String TAG="CamParaUtil";
    private static CamParaUtil mCamParaUtil=null;
    private CameraSizeComparator cameraSizeComparator=new CameraSizeComparator();

    public static CamParaUtil getInstance(){
        if (mCamParaUtil==null){
            mCamParaUtil=new CamParaUtil();
        }
        return mCamParaUtil;
    }

    public Camera.Size getPropPreviewSize(List<Camera.Size> list,float th,int minWidth){
        Collections.sort(list,cameraSizeComparator);

        int i=0;
        for (Camera.Size s:list){
            if ((s.width>=minWidth)&&equalRate(s,th)){
                break;
            }
            i++;
        }
        if (i==list.size()){
            i=0;
        }
        return list.get(i);
    }

    public Camera.Size getPropPictureSize(List<Camera.Size> list, float th, int minWidth){
        Collections.sort(list, cameraSizeComparator);

        int i = 0;
        for(Camera.Size s:list){
            if((s. width>= minWidth) && equalRate(s, th)){
                break;
            }
            i++;
        }
        if(i == list.size()){
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    private boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        return (Math.abs(r - rate) <= 0.03);
//        if(Math.abs(r - rate) <= 0.03)
//        {
//            return true;
//        }
//        else{
//            return false;
//        }
    }

    private class CameraSizeComparator implements Comparator<Camera.Size>{
        @Override
        public int compare(Camera.Size s1, Camera.Size s2) {
            if (s1.width==s2.height){
                return 0;
            }
            else if (s1.width>s2.height){
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
