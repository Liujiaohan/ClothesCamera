package com.liujiaohan.clothescamera.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.liujiaohan.clothescamera.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017-05-03.
 */

public class FragmentMain extends Fragment {
    Button CloseBtn;
    Activity mActivity;
    FragmentManager fm;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("TAG", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mActivity=getActivity();
        fm=getFragmentManager();
        ft=fm.beginTransaction();
        Log.i(TAG, "initUI: ");
        CloseBtn= (Button) mActivity.findViewById(R.id.cloth_img_btn);
        CloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity,"........",Toast.LENGTH_SHORT).show();
                Log.i("TAG", "onClick: fragmentMain");
                ft.add(R.id.fragment_container,new Fragment3());
                ft.commit();
            }
        });

    }
}
