package com.zl.hefenweather.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zl.hefenweather.annotation.AnnotationUtils;


/**
 * Created by zhangle on 2017-11-17.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnnotationUtils.injectContentView(this);
        AnnotationUtils.injectBindView(this);
        AnnotationUtils.injectOnClick(this);
    }
}
