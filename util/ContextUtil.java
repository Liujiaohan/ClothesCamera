package com.liujiaohan.clothescamera.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.ContextWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017-04-26.
 */

public class ContextUtil {
    private static Context CONTEXT;
    public static Context getContext(){
        if (CONTEXT==null){
            synchronized (ContextUtil.class){
                try {
                    Class<?> ActivityThread=Class.forName("android.app.ActivityThread");
                    Method method=ActivityThread.getMethod("currentActivityThread");
                    Object currentActivityThread=method.invoke(ActivityThread);

                    Method method1=currentActivityThread.getClass().getMethod("getApplication");
                    CONTEXT=(Context)method1.invoke(currentActivityThread);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return CONTEXT;
    }
}
