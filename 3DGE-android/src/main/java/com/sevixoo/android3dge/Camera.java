package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Matrix4f;
import com.sevixoo.android3dge.math.Vector3f;

/**
 * Created by seweryn on 22.07.2017.
 */

public class Camera {

    private Vector3f mPosition;

    private Matrix4f mProjectionMatrix;
    private Matrix4f mViewMatrix;

    Camera() {
        this.mPosition = new Vector3f(0,0,0);
        this.mViewMatrix = Matrix4f.loadIdentity();
        this.mProjectionMatrix = Matrix4f.loadIdentity();
    }

    public void lookAt(Vector3f point){

    }

    public void projection(){

    }

    public void setPosition(Vector3f position){
        mPosition = position;
    }

    public Vector3f getPosition(){
        return mPosition;
    }

    public Matrix4f getViewMatrix() {
        return mViewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return mProjectionMatrix;
    }
}
