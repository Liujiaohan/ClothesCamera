package com.liujiaohan.clothescamera.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.liujiaohan.clothescamera.R;

/**
 * Created by Administrator on 2017-04-24.
 */

public class GalleryAdapter extends BaseAdapter{
    private Context mContext;
    public int[] imagesIDs={R.mipmap.shirt,R.mipmap.ic_launcher,R.mipmap.shirt,R.mipmap.ic_launcher,R.mipmap.shirt,R.mipmap.ic_launcher};

    public GalleryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imagesIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image=new ImageView(mContext);
        image.setImageResource(imagesIDs[position]);
        image.setLayoutParams(new Gallery.LayoutParams(300,200));
        return image;
    }

    public int[] getImagesIDs(){
        return imagesIDs;
    }
}
