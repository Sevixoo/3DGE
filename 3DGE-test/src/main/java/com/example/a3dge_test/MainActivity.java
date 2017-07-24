package com.example.a3dge_test;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;
    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (LinearLayout)findViewById(R.id.activity_main);

        mGLSurfaceView = new GLSurfaceView(getBaseContext());
        mGLSurfaceView.setEGLContextClientVersion(3);
        mGLSurfaceView.setRenderer(new TestRenderer(getApplicationContext(),mGLSurfaceView));
        mContainer.addView(mGLSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mGLSurfaceView!=null) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mGLSurfaceView!=null) {
            mGLSurfaceView.onPause();
        }
    }

}
