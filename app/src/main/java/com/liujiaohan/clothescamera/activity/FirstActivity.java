package com.liujiaohan.clothescamera.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button btn = (Button) findViewById(R.id.firstpage_btn);
        ImageView img = (ImageView) findViewById(R.id.img);

        Animation translateAnimation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.animation);
        Animation translateAnimation1 = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.animation1);

        btn.startAnimation(translateAnimation1);
        img.startAnimation(translateAnimation);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FirstActivity.this, BookActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
