package com.liujiaohan.clothescamera.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.Fragment.Fragment1;
import com.liujiaohan.clothescamera.Fragment.Fragment2;
import com.liujiaohan.clothescamera.Fragment.FragmentMain;
import com.liujiaohan.clothescamera.R;

public class MainActivity extends FragmentActivity {
    ImageView imgFittingRoomBtn;
    ImageView imgClothesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        imgFittingRoomBtn= (ImageView) findViewById(R.id.fitting_room);
        imgFittingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FittingRoom.class);
                startActivity(intent);
            }
        });
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentMain fragmentMain=new FragmentMain();
        Log.i("TAG", "onClick: ");
        fragmentTransaction.add(R.id.fragment_container,fragmentMain);
        fragmentTransaction.commit();
//        imgClothesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
//                    android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                    Fragment1 fragment1=new Fragment1();
//                Log.i("TAG", "onClick: ");
//                    fragmentTransaction.add(R.id.fragment_container,fragment1);
//                    fragmentTransaction.commit();
//            }
//        });
    }
}
