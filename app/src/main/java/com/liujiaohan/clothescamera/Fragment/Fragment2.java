package com.liujiaohan.clothescamera.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liujiaohan.clothescamera.R;
import com.liujiaohan.clothescamera.activity.CertainClothesFittingActivity;
import com.liujiaohan.clothescamera.activity.FittingRoom;

/**
 * Created by Administrator on 2017-04-27.
 */

public class Fragment2 extends Fragment {
    Button tryBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tryBtn= (Button) getActivity().findViewById(R.id.btn_try);
        tryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CertainClothesFittingActivity.class));
            }
        });
    }
}
