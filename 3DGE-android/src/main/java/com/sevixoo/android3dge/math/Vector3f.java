package com.sevixoo.android3dge.math;

/**
 * Created by pi19124 on 06.07.2017.
 */

public class Vector3f {

    private float[] mData = new float[3];

    public Vector3f( float[] array ) {
        this( array[0], array[1], array[2]);
    }

    public Vector3f( Vector2f v , float z ) {
        this( v.x(), v.y(), z );
    }

    public Vector3f(float x, float y, float z) {
        this.mData[0] = x;
        this.mData[1] = y;
        this.mData[2] = z;
    }

    public float length(){
        return (float) Math.sqrt( x()*x() + y()*y() + z()*z() );
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

    public Vector3f normalize() {
        float[] product = new float[3];
        float length = length();
        if (length != 0) {
            product[0] = x()/length;
            product[1] = y()/length;
            product[2] = z()/length;
        }
        return new Vector3f(product);
    }

    public Vector3f minus(Vector3f center) {
        return new Vector3f(
                x() - center.x(),
                y() - center.y(),
                z() - center.z()
        );
    }

    public float dot(Vector3f v) {
        return x()*v.x() + y()*v.y() + z()*v.z();
    }
}
