package com.example.demo.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhangle on 2017-11-17.
 */

public class AnnotationUtils {

    public static void injectContentView(Activity activity){
        Class clazz = activity.getClass();

        //当前Activity 是否有添加ContentView 类型额注解
        if(clazz.isAnnotationPresent(ContentView.class)){

            ContentView cv = (ContentView) clazz.getAnnotation(ContentView.class);
            int layoutID = cv.value();//通过注解得到layout view 的ID 值
            try {
                Method method = clazz.getMethod("setContentView",int.class);
                method.setAccessible(true);
                method.invoke(activity,layoutID);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void injectBindView(Object object){
        View v = null;
        if(object instanceof Activity){
            v = ((Activity)object).getWindow().getDecorView();
        }else{
            v = (View)object;
        }
        Class a = object.getClass();
        Field[] fileds = a.getDeclaredFields();
        if(fileds != null ){
            for (Field field :fileds){
                field.setAccessible(true);
                BindView bindView = field.getAnnotation(BindView.class);
                if(bindView != null){
                    int viewID = bindView.value();
                    try {
                        View view = v.findViewById(viewID);
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void injectBindView(Object object,View v){
        Class a = object.getClass();
        Field[] fileds = a.getDeclaredFields();
        if(fileds != null ){
            for (Field field :fileds){
                field.setAccessible(true);
                BindView bindView = field.getAnnotation(BindView.class);
                if(bindView != null){
                    int viewID = bindView.value();
                    try {
                        View view = v.findViewById(viewID);
                        field.set(object,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void injectOnClick(final Activity activity){
        Class a = activity.getClass();
        Method[] methods = a.getDeclaredMethods();
        if(methods != null) {
            for(final Method method :methods) {
                if(method.isAnnotationPresent(OnClick.class)){
                    OnClick onClick = method.getAnnotation(OnClick.class);
                    int[] viewIDs = onClick.value();
                    for (int viewID : viewIDs) {
                        final View view = activity.findViewById(viewID);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(activity,view);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }

            }
        }

    }
}
