package com.example.demo.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.demo.annotation.AnnotationUtils;

/**
 * Created by zhangle on 2017-11-17.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnnotationUtils.injectContentView(this);
        AnnotationUtils.injectBindView(this);
        AnnotationUtils.injectOnClick(this);
    }
}
