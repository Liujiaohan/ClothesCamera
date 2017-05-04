package com.liujiaohan.clothescamera.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-04-23.
 */

public class FileUtil {
    private static final String TAG="FileUtil";
    private static final File mainPath= Environment.getExternalStorageDirectory();
    private static String storagePath="";
    public static Uri lastImgUri;

    /*
    * init save path
    */

    private static String initPath(){
        if ("".equals(storagePath)){
            storagePath=mainPath.getAbsolutePath()+"/"+"ClothesCamera";
            File f=new File(storagePath);
            if (!f.exists()){
                f.mkdir();
            }
        }
        return storagePath;
    }

    public static void saveBitmap(Bitmap b){
        String path=initPath();
        long data=System.currentTimeMillis();
        String pictureName=path+"/"+data+".jpg";
        try {
            lastImgUri=Uri.fromFile(new File(pictureName));
            FileOutputStream fos=new FileOutputStream(pictureName);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG,100,bos);
            bos.flush();
            bos.close();
            Log.i(TAG,"save Bitmap successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
