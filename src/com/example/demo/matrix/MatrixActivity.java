package com.example.demo.matrix;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.demo.R;


public class MatrixActivity extends Activity {

    private ImageView view1,view2,view3,view4,view5,view6;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_layout);
        view1  = (ImageView) findViewById(R.id.iv_1);
        view2  = (ImageView) findViewById(R.id.iv_2);
        view3  = (ImageView) findViewById(R.id.iv_3);
        view4  = (ImageView) findViewById(R.id.iv_4);
        view5  = (ImageView) findViewById(R.id.iv_5);
        view6  = (ImageView) findViewById(R.id.iv_6);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.dock_bg_selected);
        /**
         * 旋转中心为(0,0)
         */
        Matrix matrix = getMatrix();
        Bitmap bit = getBitmap(bitmap, matrix);
        view1.setImageBitmap(bit);

        /**
         * 旋转中心为(0,height/2)
         */
        matrix = getMatrix();
        matrix.preTranslate(0, -bitmap.getHeight() / 2);
        matrix.postTranslate(0, bitmap.getHeight() / 2);
        bit = getBitmap(bitmap, matrix);
        view2.setImageBitmap(bit);

        /**
         * 旋转中心为(0,height)
         */
        matrix = getMatrix();
        matrix.preTranslate(0, -bitmap.getHeight());
        matrix.postTranslate(0,bitmap.getHeight());
        bit = getBitmap(bitmap, matrix);
        view3.setImageBitmap(bit);
        
        
        /**
         * 旋转中心为(0,0)
         */
        matrix = getMatrix();
        bit = getBitmap(bitmap, matrix);
        view4.setImageBitmap(bit);
        
        /**
         * 旋转中心为(0,height/2)
         */
        matrix = getMatrix();
        matrix.preTranslate(0, -bitmap.getHeight() / 2);
        //matrix.postTranslate(0, bitmap.getHeight() / 2);
        bit = getBitmap(bitmap, matrix);
        view5.setImageBitmap(bit);
        
        /**
         * 旋转中心为(0,height)
         */
        matrix = getMatrix();
        matrix.preTranslate(0, -bitmap.getHeight());
        matrix.postTranslate(0,bitmap.getHeight());
        bit = getBitmap(bitmap, matrix);
        view6.setImageBitmap(bit);
    }

    private Bitmap getBitmap(Bitmap bitmap, Matrix matrix) {
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    private Matrix getMatrix(){
        Matrix matrix = new Matrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateY(45);
        camera.getMatrix(matrix);
        camera.restore();
        return matrix;
    }
}