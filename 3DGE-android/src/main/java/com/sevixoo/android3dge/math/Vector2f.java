package com.sevixoo.android3dge.math;

/**
 * Created by pi19124 on 06.07.2017.
 */

public class Vector2f {

    private float[] mData = new float[2];

    public Vector2f(float x, float y) {
        this.mData[0] = x;
        this.mData[1] = y;
    }

    public Vector2f(float[] data) {
        this.mData[0] = data[0];
        this.mData[1] = data[0];
    }

    public float x(){
        return mData[0];
    }

    public float y(){
        return mData[1];
    }

    float[] get() {
        return mData;
    }
}
