package com.liujiaohan.clothescamera.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.liujiaohan.clothescamera.Camera.CameraManager;

/**
 * Created by Administrator on 2017-04-26.
 */

public class ShareUtil {
    public static void sharePhotoToFriend(Bitmap bitmap, Context context){
        final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(ContextUtil.getContext().getContentResolver(), CameraManager.getInstance().getNewBitmap(), null,null));
        Intent intent=new Intent();
        ComponentName comp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(intent);
    }

    public static void sharePhotoToFriendsLine(Bitmap bitmap,Context context){
        final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(ContextUtil.getContext().getContentResolver(), CameraManager.getInstance().getNewBitmap(), null,null));
        Intent intent=new Intent();
        ComponentName comp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(intent);
    }

    public static void shareBitmap(Bitmap bitmap,Activity activity){
        final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(ContextUtil.getContext().getContentResolver(),
                CameraManager.getInstance().getNewBitmap(), null,null));
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        activity.startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
    }

}
