package com.liujiaohan.clothescamera.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.R;

public class BookActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            ImageView java = (ImageView) findViewById(R.id.java);
            ImageView python = (ImageView) findViewById(R.id.py);
            ImageView c = (ImageView) findViewById(R.id.c);
            java.setOnClickListener(this);
            python.setOnClickListener(this);
            c.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(BookActivity.this, ChooseActivity.class);
        switch (v.getId()) {
            case R.id.java: {
                intent.putExtra("type", 1);
                break;
            }
            case R.id.py: {
                intent.putExtra("type", 2);
                break;
            }
            case R.id.c: {
                intent.putExtra("type", 3);
                break;
            }

        }
    }
}
