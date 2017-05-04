package com.liujiaohan.clothescamera.Fragment;


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

import com.liujiaohan.clothescamera.R;

/**
 * Created by Administrator on 2017-04-27.
 */

public class Fragment1 extends Fragment {
    private Button btnCloth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    initUI();
        Log.i("TAG", "onCreateView: ");
        return inflater.inflate(R.layout.fragment1,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI() {
        btnCloth= (Button) getActivity().findViewById(R.id.btn_cloth);
        btnCloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Fragment1", "onClick: ");
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container,new Fragment2());
                ft.commit();
            }
        });
    }
}
