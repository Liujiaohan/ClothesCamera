package com.liujiaohan.clothescamera.activity;

import android.content.Context;
import android.graphics.Point;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.liujiaohan.clothescamera.Camera.CameraManager;
import com.liujiaohan.clothescamera.R;
import com.liujiaohan.clothescamera.ViewPage.ImageGallery;
import com.liujiaohan.clothescamera.ViewPage.ThreeDTransformer;
import com.liujiaohan.clothescamera.adapter.ThreeDGalleryAdapter;
import com.liujiaohan.clothescamera.preview.CameraSurfaceView;
import com.liujiaohan.clothescamera.util.DisplayUtil;
import com.liujiaohan.clothescamera.util.ShareUtil;

public class FittingRoom extends AppCompatActivity implements View.OnClickListener,CameraManager.CamOpenOverCallback {
    private static final String TAG="CameraActivity";
    private CameraSurfaceView mCameraSurfaceView;
    private ImageGallery imgGallery;
    private float previewRate=-1f;
    static Context context=null;
    private ViewSwitcher viewSwitcher;
    private ImageButton btnShare;
    private int[] clothesList={R.mipmap.c01,R.mipmap.c02,R.mipmap.c04,R.mipmap.c05,R.mipmap.h07,
            R.mipmap.h08,R.mipmap.h09,R.mipmap.s10,R.mipmap.s11,R.mipmap.s12,R.mipmap.s13,R.mipmap.s14};
    private int[] hatsList={R.mipmap.h07,R.mipmap.h08,R.mipmap.h09};
    private int[] shoesList={R.mipmap.s10,R.mipmap.s11,R.mipmap.s12,R.mipmap.s13,R.mipmap.s14};

    Button btnTake;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;

        setContentView(R.layout.activity_fitting_room);

        initUI();
        initViewParams();
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
        mCameraSurfaceView= (CameraSurfaceView) findViewById(R.id.fitting_room_camera);
        imgGallery= (ImageGallery) findViewById(R.id.img_gallery);
        imgGallery.setOffscreenPageLimit(3);
        int pagerWidth= (int) (getResources().getDisplayMetrics().widthPixels);
        ViewGroup.LayoutParams lp=imgGallery.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        imgGallery.setLayoutParams(lp);
        imgGallery.setPageMargin(0);
    //    imgGallery.setAdapter(new ThreeDGalleryAdapter(clothesList));
        imgGallery.setPageTransformer(true,new ThreeDTransformer());

        findViewById(R.id.activity_fitting_room).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return imgGallery.dispatchTouchEvent(motionEvent);
            }
        });

        viewSwitcher= (ViewSwitcher) findViewById(R.id.viewSwitch);
        btnSave= (Button) findViewById(R.id.btn_save);
        btnTake= (Button) findViewById(R.id.btn_take_photo);
        btnShare= (ImageButton) findViewById(R.id.btn_share);

        btnTake.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_take_photo:
                viewSwitcher.showPrevious();
                Log.i(TAG, "onClick: take photo");
                CameraManager.getInstance().doTakePicture();
                imgGallery.setScrollable(false);
                btnShare.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save:
                Log.i(TAG,"save");
                CameraManager.getInstance().doSave(clothesList[imgGallery.getCurrentItem()]);
                CameraManager.getInstance().rePreview();
                imgGallery.setScrollable(true);
                Toast.makeText(this,"save successfully",Toast.LENGTH_SHORT).show();
                viewSwitcher.showNext();
                break;
            case R.id.btn_share:
                Log.i(TAG,"share");
                btnShare.setVisibility(View.INVISIBLE);
                CameraManager.getInstance().doSave(clothesList[imgGallery.getCurrentItem()]);
                ShareUtil.shareBitmap(CameraManager.getInstance().getNewBitmap(),this);
                imgGallery.setScrollable(true);
                viewSwitcher.showNext();
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
        super.onRestart();
//        if (!CameraManager.getInstance().isPreviewing()){
//            CameraManager.getInstance().stopPreview();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
