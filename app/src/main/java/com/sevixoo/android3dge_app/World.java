package com.sevixoo.android3dge_app;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pi19124 on 27.06.2017.
 */

public class World {

    private final Camera mCamera;

    private List<Object3D> mObject3DList = new ArrayList<>();

    public World() {
        this.mCamera = new Camera();
    }

    public Camera getCamera() {
        return mCamera;
    }

    public float[] getVPMatrix() {
        float[] matrix = new float[16];
        Matrix.multiplyMM(matrix, 0, mCamera.getProjectionMatrix() , 0, mCamera.getViewMatrix(), 0);
        return matrix;
    }

    public void add( Object3D object3D ){
        mObject3DList.add(object3D);
    }

    public void display() {
        float[] vpMatrix = getVPMatrix();
        for (Object3D object3D : mObject3DList) {
            object3D.draw(vpMatrix);
        }
    }

    public void draw(FrameBuffer frameBuffer ) {
        frameBuffer.bind();
        float[] vpMatrix = getVPMatrix();
        for (Object3D object3D : mObject3DList) {
            object3D.draw(vpMatrix);
        }
        frameBuffer.unbind();

         /*GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glStencilOp(GLES20.GL_KEEP,GLES20.GL_KEEP,GLES20.GL_REPLACE);

        GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 0xFF);
        GLES20.glStencilMask(0xFF);

        mObject3D.draw(vpMatrix);

        GLES20.glStencilFunc(GLES20.GL_NOTEQUAL, 1, 0xFF);
        GLES20.glStencilMask(0x00);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        mScaledObject3D.draw(vpMatrix);

        GLES20.glStencilMask(0xFF);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);*/


    }
}
