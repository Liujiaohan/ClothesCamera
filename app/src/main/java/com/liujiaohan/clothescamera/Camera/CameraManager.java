package com.liujiaohan.clothescamera.Camera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;

import com.liujiaohan.clothescamera.R;
import com.liujiaohan.clothescamera.activity.CameraActivity;
import com.liujiaohan.clothescamera.activity.FittingRoom;
import com.liujiaohan.clothescamera.util.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017-04-23.
 */

public class CameraManager {
    private static final String TAG="CameraManager";
    private static CameraManager mCameraManager;
    public android.hardware.Camera mCamera;
    private boolean isPreviewing=false;
    private android.hardware.Camera.Parameters mParameters;
    private float mPreviewRate=-1f;
    private Bitmap newBitmap;
    private int w;
    private int h;


    public static synchronized CameraManager getInstance(){
        if (mCameraManager==null){
            mCameraManager=new CameraManager();
        }
        return mCameraManager;
    }

    public void openCamera(CamOpenOverCallback callback){
        Log.i(TAG, "openCamera: opening");
        mCamera= Camera.open();
        Log.i(TAG, "openCamera: hasOpened");
        callback.cameraHasOpened();
    }

    /**
     * start preview*/
    public void doStarPreview(SurfaceHolder holder,float previewRate){
        if (isPreviewing){
            mCamera.stopPreview();
            return;
        }

        if (mCamera!=null){
            mParameters=mCamera.getParameters();
            mParameters.setPictureFormat(PixelFormat.JPEG);
            Camera.Size pictureSize=
                    CamParaUtil.getInstance().getPropPictureSize(mParameters.getSupportedPictureSizes(),previewRate,800);
            mParameters.setPictureSize(pictureSize.width,pictureSize.height);
            Camera.Size previewSize=
                    CamParaUtil.getInstance().getPropPreviewSize(mParameters.getSupportedPreviewSizes(),previewRate,800);
            mParameters.setPreviewSize(previewSize.width,previewSize.height);

            mCamera.setDisplayOrientation(90);

            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

            mCamera.setParameters(mParameters);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

            isPreviewing=true;
            mPreviewRate=previewRate;

            mParameters=mCamera.getParameters();
        }
    }

    public void doStopCamera(){
        if (mCamera!=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing=false;
            mPreviewRate=-1f;
            mCamera.release();
            mCamera=null;
        }
    }

    public void doTakePicture(){
        if (isPreviewing&&(mCamera!=null)){
            AudioManager audioManager = (AudioManager) ContextUtil.getContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
            mCamera.takePicture(mShutterCallback,null,mJpegPictureCallback);
            final Handler soundHandler = new Handler();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    soundHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AudioManager audioManager = (AudioManager)ContextUtil.getContext().getSystemService(Context.AUDIO_SERVICE);                   audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                        }
                    });
                }
            }, 1000);
        }
    }


    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback()
            //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    {
        public void onShutter() {
            // TODO Auto-generated method stub
        }
    };

    // 拍摄的未压缩原数据的回调,可以为null
    Camera.PictureCallback mRawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(TAG, "myRawCallback:onPictureTaken...");
        }
    };

    //对jpeg图像数据的回调,最重要的一个回调
    Camera.PictureCallback mJpegPictureCallback = new Camera.PictureCallback()
            //对jpeg图像数据的回调,最重要的一个回调
    {
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(TAG, "myJpegCallback:onPictureTaken...");
            Bitmap b = null;

            if(null != data){
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图

                newBitmap=b.copy(Bitmap.Config.ARGB_8888, true);
                newBitmap = ImageUtil.getRotateBitmap(newBitmap, 90.0f);
                w=newBitmap.getWidth();
                h=newBitmap.getHeight();

            //    newBitmap=mergeBitmap(b,clothesBitmap);
                mCamera.stopPreview();
                isPreviewing = false;
            }

        }
    };

    public void rePreview(){
        //再次进入预览
        mCamera.startPreview();
        isPreviewing = true;
    }

    public void stopPreview(){
        mCamera.stopPreview();
        isPreviewing=false;
    }
    public void handleZoom(boolean isZoomOut) {
        Camera.Parameters params = mCamera.getParameters();
        if (params.isZoomSupported()) {
            int maxZoom = params.getMaxZoom();
            int zoom = params.getZoom();
            if (isZoomOut && zoom < maxZoom) {
                zoom+=2;
            } else if (zoom > 0) {
                zoom-=2;
            }
            Log.i(TAG, "handleZoom: ");
            params.setZoom(zoom);
            mCamera.setParameters(params);
        } else {
            Log.i(TAG, "zoom not supported");
        }
    }

    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }

    public void doSave(int clothId){
        Bitmap clothesBitmap=null;
        clothesBitmap=BitmapFactory.decodeResource(ContextUtil.getContext().getResources(), clothId);
        int clothesWidth=clothesBitmap.getWidth();
        int clothesHeight=clothesBitmap.getHeight();
        //保存图片到sdcard
        if(null != newBitmap)
        {
            Canvas canvas=new Canvas(newBitmap);
            canvas.drawBitmap(clothesBitmap,Math.abs(w-clothesWidth)/2,Math.abs(h-clothesHeight)/2,new Paint());
            //设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。
            //图片竟然不能旋转了，故这里要旋转下
            FileUtil.saveBitmap(newBitmap);
        }
        clothesBitmap.recycle();
    }
    public interface CamOpenOverCallback{
        public void cameraHasOpened();
    }

    public Bitmap getNewBitmap(){
        return newBitmap;
    }

    public boolean isPreviewing(){
        return isPreviewing;
    }
}
