package com.example.a3dge_test;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.sevixoo.android3dge.debug.CameraManipulator;
import com.sevixoo.android3dge.system.Input;
import com.sevixoo.android3dge.debug.ObjectManipulator;
import com.sevixoo.android3dge.math.Vector2f;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GLSurfaceView mGLSurfaceView;
    private LinearLayout mContainer;

    private ObjectManipulator mObjectManipulator;
    private CameraManipulator mCameraManipulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (LinearLayout)findViewById(R.id.gles_context_holder);

        mObjectManipulator = new ObjectManipulator();
        mCameraManipulator = new CameraManipulator();

        mGLSurfaceView = new GLSurfaceView(getBaseContext());
        mGLSurfaceView.setEGLContextClientVersion(3);
        mGLSurfaceView.setRenderer(new TestRenderer(getApplicationContext(),mGLSurfaceView,
                mObjectManipulator,mCameraManipulator));
        mGLSurfaceView.setOnTouchListener(this);
        mContainer.addView(mGLSurfaceView);

        findViewById(R.id.button_obj_trans_XY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObjectManipulator.setTransformType(ObjectManipulator.TRANSFORM_TRANSLATE);
            }
        });
        findViewById(R.id.button_obj_rotate_XY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObjectManipulator.setTransformType(ObjectManipulator.TRANSFORM_ROTATION);
            }
        });
        findViewById(R.id.button_obj_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObjectManipulator.setTransformType(ObjectManipulator.TRANSFORM_SCALE);
            }
        });
        findViewById(R.id.button_obj_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMainMenu();
            }
        });
        findViewById(R.id.button_cam_trans_xy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraManipulator.setTransformType(CameraManipulator.TRANSFORM_POSITION_XY);
            }
        });
        findViewById(R.id.button_cam_trans_Z).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraManipulator.setTransformType(CameraManipulator.TRANSFORM_POSITION_Z);
            }
        });
        findViewById(R.id.button_cam_center_XY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraManipulator.setTransformType(CameraManipulator.TRANSFORM_CENTER_XY);
            }
        });
        findViewById(R.id.button_cam_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMainMenu();
            }
        });
        findViewById(R.id.button_cam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.linearLayout_cam).setVisibility(View.VISIBLE);
                findViewById(R.id.main_holder).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.button_obj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.linearLayout_obj).setVisibility(View.VISIBLE);
                findViewById(R.id.main_holder).setVisibility(View.GONE);
            }
        });
    }

    private void displayMainMenu(){
        findViewById(R.id.linearLayout_cam).setVisibility(View.GONE);
        findViewById(R.id.linearLayout_obj).setVisibility(View.GONE);
        findViewById(R.id.main_holder).setVisibility(View.VISIBLE);
        mCameraManipulator.setTransformType(0);
        mObjectManipulator.setTransformType(0);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE ||
                motionEvent.getAction() == MotionEvent.ACTION_DOWN ){
            Input.mCurrentPointer = new Vector2f(motionEvent.getX(),motionEvent.getY());
        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            Input.mCurrentPointer = null;
        }

        return true;
    }
}
