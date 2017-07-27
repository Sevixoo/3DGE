package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Matrix4f;
import com.sevixoo.android3dge.math.Vector3f;

/**
 * Created by seweryn on 22.07.2017.
 */

public class Camera {

    private Vector3f mPosition;

    private Matrix4f mProjectionMatrix;

    private Vector3f mCenter;
    private Vector3f mUp;

    Camera() {
        this.mPosition = new Vector3f(0,0,0);
        this.mProjectionMatrix = Matrix4f.loadIdentity();
        lookAt(new Vector3f(0,0,0),new Vector3f(0,1,0) );
    }

    public void lookAt(Vector3f point, Vector3f up){
        mCenter = point;
        mUp = up;
    }

    public void orthogonalProjection(float left, float right, float top, float bottom, float near, float far){
        mProjectionMatrix = Matrix4f.ortho(left, right, top, bottom, near, far);
    }

    public void setPosition(Vector3f position){
        mPosition = position;
    }

    public Vector3f getPosition(){
        return mPosition;
    }

    public Vector3f getCenter(){
        return mCenter;
    }

    public Matrix4f getViewMatrix() {
        return Matrix4f.lookAt(mPosition,mCenter,mUp);
    }

    public Matrix4f getProjectionMatrix() {
        return mProjectionMatrix;
    }
}
