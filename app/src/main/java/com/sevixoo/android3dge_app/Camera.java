package com.sevixoo.android3dge_app;

import android.opengl.Matrix;

/**
 * Created by pi19124 on 27.06.2017.
 */

public class Camera {

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float[] mPosition;

    Camera() {
        mPosition = new float[]{ 0, 0, 0};
    }

    public void setProjection(int width, float height) {
        setProjection(0, width, 0, height);
    }

    public void setProjection(float left, float right, float bottom, float top){
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top , 3, 10000);
    }

    public void setPosition(float x, float y, float z){
        mPosition = new float[]{ x, y, z};
    }

    public void lookAt(float x, float y, float z){
        Matrix.setLookAtM(mViewMatrix, 0,
                mPosition[0], mPosition[1], mPosition[2],
                x, y, z, 0f, 1.0f, 0.0f
        );
    }

    float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }

    float[] getVPMatrix(){
        float[] mVPMatrix = new float[16];
        Matrix.multiplyMM(mVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        return mVPMatrix;
    }

    float[] getViewMatrix() {
        return mViewMatrix;
    }
}
