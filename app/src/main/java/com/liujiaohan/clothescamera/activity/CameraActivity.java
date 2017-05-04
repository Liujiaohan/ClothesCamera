package com.liujiaohan.clothescamera.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.Camera.CameraManager;
import com.liujiaohan.clothescamera.R;
import com.liujiaohan.clothescamera.adapter.GalleryAdapter;
import com.liujiaohan.clothescamera.preview.CameraSurfaceView;
import com.liujiaohan.clothescamera.util.DisplayUtil;

public class CameraActivity extends AppCompatActivity implements CameraManager.CamOpenOverCallback,View.OnClickListener{
    private static final String TAG="CameraActivity";
    CameraSurfaceView mCameraSurfaceView;
    Button mShutterButton;
    ImageView mClothesImg;
    Gallery clothesGallery;
    GalleryAdapter mGalleryAdapter;
    float previewRate=-1f;
    static Context context=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;

        setContentView(R.layout.activity_camera);

        initUI();
        initViewParams();

        mShutterButton.setOnClickListener(this);

    }

    public static Context getMyContext(){
        return context;
    }

    private void initViewParams() {
        ViewGroup.LayoutParams params=mCameraSurfaceView.getLayoutParams();
        Point p= DisplayUtil.getScreenMetrics(this);
        params.width=p.x;
        params.height=p.y;
        previewRate=DisplayUtil.getScreenRate(this);
        mCameraSurfaceView.setLayoutParams(params);

    }

    private void initUI() {
        mCameraSurfaceView= (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
        mShutterButton= (Button) findViewById(R.id.btn_shutter);
        mClothesImg= (ImageView) findViewById(R.id.cloth_img);
        clothesGallery= (Gallery) findViewById(R.id.clothesGallery);
        mGalleryAdapter=new GalleryAdapter(this);
        clothesGallery.setAdapter(mGalleryAdapter);
        clothesGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int[] imageIDs=mGalleryAdapter.getImagesIDs();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mClothesImg.setImageResource(imageIDs[position]);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_shutter:
                CameraManager.getInstance().doTakePicture();
                break;
            default:
                break;
        }
    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder= null;
        try {
            holder = mCameraSurfaceView.getSurfaceHolder();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "cameraHasOpened: "+mCameraSurfaceView.toString());
        }
        CameraManager.getInstance().doStarPreview(holder,previewRate);
    }

    @Override
    protected void onRestart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CameraManager.getInstance().openCamera(CameraActivity.this);
            }
        }).start();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
