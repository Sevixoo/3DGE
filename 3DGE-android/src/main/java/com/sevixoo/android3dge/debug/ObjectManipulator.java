package com.sevixoo.android3dge.debug;

import com.sevixoo.android3dge.system.Device;
import com.sevixoo.android3dge.system.Input;
import com.sevixoo.android3dge.math.Vector3f;

/**
 * Created by seweryn on 26.07.2017.
 */

public class ObjectManipulator {

    public static final int TRANSFORM_ROTATION = 1;
    public static final int TRANSFORM_TRANSLATE = 2;
    public static final int TRANSFORM_SCALE = 3;

    private WavefrontObject mTarget;

    private int mSelectedType = 0;

    public ObjectManipulator() {}

    public void setTarget(WavefrontObject object){
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
                if (mSelectedType == TRANSFORM_ROTATION) {
                    mTarget.rotateX(mTarget.getRotation().x() + dY * 180);
                    mTarget.rotateY(mTarget.getRotation().y() + dX * 180);
                }else if (mSelectedType == TRANSFORM_TRANSLATE) {
                    mTarget.translate(new Vector3f(
                            dX * 10f +  mTarget.getTranslation().x(),
                            dY * 10f +  mTarget.getTranslation().y(),
                            mTarget.getTranslation().z()
                    ));
                }else if (mSelectedType == TRANSFORM_SCALE) {
                    mTarget.scale(new Vector3f(
                            dX * 2f + mTarget.getScale().x(),
                            dX * 2f + mTarget.getScale().y(),
                            dX * 2f + mTarget.getScale().z()
                    ));
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
