package com.sevixoo.android3dge.math;

/**
 * Created by pi19124 on 06.07.2017.
 */

public class Vector4f {

    private float[] mData = new float[4];

    public Vector4f( float[] array ) {
        this( array[0], array[1], array[2], array[3] );
    }

    public Vector4f( Vector2f v , float z, float w) {
        this( v.x(), v.y(), z, w );
    }

    public Vector4f( Vector3f v , float w) {
        this( v.x(), v.y(), v.z(), w );
    }

    public Vector4f(float x, float y, float z, float w) {
        this.mData[0] = x;
        this.mData[1] = y;
        this.mData[2] = z;
        this.mData[3] = w;
    }

    public float x(){
        return mData[0];
    }

    public float y(){
        return mData[1];
    }

    public float z(){
        return mData[2];
    }

    public float w(){
        return mData[3];
    }

    public Vector2f xy(){
        return new Vector2f(x(),y());
    }

    double get(int i) {
        return mData[i];
    }

    public Vector3f xyz() {
        return new Vector3f(x(),y(),z());
    }
}
