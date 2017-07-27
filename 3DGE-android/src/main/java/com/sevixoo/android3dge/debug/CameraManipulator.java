package com.sevixoo.android3dge.debug;

import com.sevixoo.android3dge.Camera;
import com.sevixoo.android3dge.system.Device;
import com.sevixoo.android3dge.system.Input;
import com.sevixoo.android3dge.math.Vector3f;

/**
 * Created by seweryn on 27.07.2017.
 */

public class CameraManipulator {

    public static final int TRANSFORM_POSITION_XY = 1;
    public static final int TRANSFORM_POSITION_Z = 2;
    public static final int TRANSFORM_CENTER_XY = 3;
    public static final int TRANSFORM_FAR_NEAR = 4;

    private Camera mTarget;

    private int mSelectedType = 0;

    public CameraManipulator( ) { }

    public void setTarget(Camera object){
        mTarget = object;
    }

    public void setTransformType( int type ){
        mSelectedType = type;
    }

    private Float lastX;
    private Float lastY;

    public void onUpdate(){
        if(Input.mCurrentPointer!=null){
            if(lastX != null){
                float dX = (Input.mCurrentPointer.x() - lastX)/ Device.mWidth;
                float dY = (Input.mCurrentPointer.y() - lastY)/Device.mHeight;
                if (mSelectedType == TRANSFORM_POSITION_XY) {
                    mTarget.setPosition(new Vector3f(
                            mTarget.getPosition().x() + dX * 10 ,
                            mTarget.getPosition().y() + dY * 10 ,
                            mTarget.getPosition().z()
                    ));
                } else if (mSelectedType == TRANSFORM_POSITION_Z) {
                    mTarget.setPosition(new Vector3f(
                            mTarget.getPosition().x(),
                            mTarget.getPosition().y(),
                            mTarget.getPosition().z() + dX * 10
                    ));
                } else if (mSelectedType == TRANSFORM_CENTER_XY) {
                    mTarget.lookAt(new Vector3f(
                            mTarget.getCenter().x() + dX * 10 ,
                            mTarget.getCenter().y() + dY * 10 ,
                            mTarget.getCenter().z()),
                            new Vector3f(0.0f,1.0f,0.0f)
                    );
                }
            }
            lastX = Input.mCurrentPointer.x();
            lastY = Input.mCurrentPointer.y();
        }else{
            lastX = null;
            lastY = null;
        }
    }

}
