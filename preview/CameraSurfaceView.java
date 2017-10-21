package com.liujiaohan.clothescamera.preview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.liujiaohan.clothescamera.Camera.CameraManager;
import com.liujiaohan.clothescamera.activity.CameraActivity;

/**
 * Created by Administrator on 2017-04-23.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG="CameraSurfaceView";
    private CameraManager mCameraManager;
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private float oldDist=-1f;

    public CameraSurfaceView(Context context) {
        super(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mSurfaceHolder=getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CameraManager.getInstance().openCamera((CameraManager.CamOpenOverCallback)mContext);
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraManager.getInstance().doStopCamera();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2)
            {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.i(TAG, "onTouchEvent: down");
                    oldDist = getFingerSpacing(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG, "onTouchEvent: down");
                    float newDist = getFingerSpacing(event);
                    if (newDist > oldDist) {
                        CameraManager.getInstance().handleZoom(true);
                    } else if (newDist < oldDist) {
                        CameraManager.getInstance().handleZoom(false);
                    }
                    oldDist = newDist;
                    break;
            }
        }
        return true;
    }


    private static float getFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    public SurfaceHolder getSurfaceHolder(){
        return mSurfaceHolder;
    }
}
