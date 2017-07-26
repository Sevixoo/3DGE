package com.sevixoo.android3dge.math;

import android.opengl.Matrix;

/**
 * Created by seweryn on 19.07.2017.
 */

public class Matrix4f {

    private float[] mData = new float[16];

    public Matrix4f(){
        setIdentity();
    }

    public Matrix4f(float[] matrix) {
        for (int i=0; i<16; i++)
            mData[i] = matrix[i];
    }

    public Matrix4f(Vector2f v1, Vector2f v2) {
        this.mData[0] = v1.x();
        this.mData[1] = v1.y();
        this.mData[3] = v2.x();
        this.mData[4] = v2.y();
    }

    public void setIdentity() {
        for (int i=0; i<4; i++)
            for (int j=0; j<4; j++)
                mData[i*4 + j] = i == j ? 1.0f : 0.0f;
    }

    public float[] get() {
        return mData;
    }

    public boolean equals (Object object) {
        Matrix4f matrix = (Matrix4f) object;
        for (int i=0; i<16; i++)
            if (mData[i] != matrix.mData[i]) return false;
        return true;
    }

    public Matrix4f add (Matrix4f matrix) {
        Matrix4f product = new Matrix4f(mData);
        for (int i=0; i<4; i++)
            for (int j=0; j<4; j++)
                product.mData[i*4 + j] += matrix.mData[i*4 + j];
        return product;
    }

    public Matrix4f multiply(Matrix4f matrix) {
        Matrix4f product = new Matrix4f();
        for (int i = 0; i < 16; i += 4) {
            for (int j = 0; j < 4; j++) {
                product.mData[i + j] = 0.0f;
                for (int k = 0; k < 4; k++)
                    product.mData[i + j] += mData[i + k] * matrix.mData[k*4 + j];
            }
        }
        return product;
    }

    public Vector4f multiply (Vector4f vector4) {
        float[] product = new float[4];
        for (int i = 0; i < 4; i++) {
            float value = 0.0f;
            for (int j = 0; j < 4; j++)
                value += get(i, j) * vector4.get(j);
            product[i] = value;
        }
        return new Vector4f(product);
    }

    float get(int i, int j) {
        return mData[i*4 + j];
    }

    public Vector2f transform(Vector2f v) {
        return new Vector2f(transformPoint(v.get()));
    }

    public float[] transformPoint (float[] point) {
        float[]  result = new float[3];

        result[0] = point[0] * mData[0]  +
                point[1] * mData[4]  +
                point[2] * mData[8]  + mData[12];

        result[1] = point[0] * mData[1]  +
                point[1] * mData[5]  +
                point[2] * mData[9]  + mData[13];

        result[2] = point[0] * mData[2]   +
                point[1] * mData[6]   +
                point[2] * mData[10]  + mData[14];
        return result;
    }

    /**
     * Invert this 4x4 matrix.
     */
    private void invert() {
        float[] tmp = new float[12];
        float[] src = new float[16];
        float[] dst = new float[16];

        // Transpose matrix
        for (int i = 0; i < 4; i++) {
            src[i +  0] = mData[i*4 + 0];
            src[i +  4] = mData[i*4 + 1];
            src[i +  8] = mData[i*4 + 2];
            src[i + 12] = mData[i*4 + 3];
        }

        // Calculate pairs for first 8 elements (cofactors)
        tmp[0] = src[10] * src[15];
        tmp[1] = src[11] * src[14];
        tmp[2] = src[9]  * src[15];
        tmp[3] = src[11] * src[13];
        tmp[4] = src[9]  * src[14];
        tmp[5] = src[10] * src[13];
        tmp[6] = src[8]  * src[15];
        tmp[7] = src[11] * src[12];
        tmp[8] = src[8]  * src[14];
        tmp[9] = src[10] * src[12];
        tmp[10] = src[8] * src[13];
        tmp[11] = src[9] * src[12];

        // Calculate first 8 elements (cofactors)
        dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
        dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
        dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
        dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
        dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
        dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
        dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
        dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
        dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
        dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
        dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
        dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
        dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
        dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
        dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
        dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];

        // Calculate pairs for second 8 elements (cofactors)
        tmp[0]  = src[2]*src[7];
        tmp[1]  = src[3]*src[6];
        tmp[2]  = src[1]*src[7];
        tmp[3]  = src[3]*src[5];
        tmp[4]  = src[1]*src[6];
        tmp[5]  = src[2]*src[5];
        tmp[6]  = src[0]*src[7];
        tmp[7]  = src[3]*src[4];
        tmp[8]  = src[0]*src[6];
        tmp[9]  = src[2]*src[4];
        tmp[10] = src[0]*src[5];
        tmp[11] = src[1]*src[4];

        // Calculate second 8 elements (cofactors)
        dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
        dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
        dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
        dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
        dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
        dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
        dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
        dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
        dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
        dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
        dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
        dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
        dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
        dst[14] -= tmp[10]* src[11 ] + tmp[2] * src[8]   + tmp[7] * src[9];
        dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
        dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

        // Calculate determinant
        float det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];

        // Calculate matrix inverse
        det = 1.0f / det;
        for (int i = 0; i < 16; i++)
            mData[i] = dst[i] * det;
    }

    public void scale( float sx, float sy, float sz ) {
        mData[0 * 4 + 0] *= sx;
        mData[0 * 4 + 1] *= sx;
        mData[0 * 4 + 2] *= sx;
        mData[0 * 4 + 3] *= sx;

        mData[1 * 4 + 0] *= sy;
        mData[1 * 4 + 1] *= sy;
        mData[1 * 4 + 2] *= sy;
        mData[1 * 4 + 3] *= sy;

        mData[2 * 4 + 0] *= sz;
        mData[2 * 4 + 1] *= sz;
        mData[2 * 4 + 2] *= sz;
        mData[2 * 4 + 3] *= sz;
    }

    public Matrix4f translate( float tx, float ty, float tz ) {
        mData[3 * 4 + 0] += ( mData[0 * 4 + 0] * tx + mData[1 * 4 + 0]
                * ty + mData[2 * 4 + 0] * tz );
        mData[3 * 4 + 1] += ( mData[0 * 4 + 1] * tx + mData[1 * 4 + 1]
                * ty + mData[2 * 4 + 1] * tz );
        mData[3 * 4 + 2] += ( mData[0 * 4 + 2] * tx + mData[1 * 4 + 2]
                * ty + mData[2 * 4 + 2] * tz );
        mData[3 * 4 + 3] += ( mData[0 * 4 + 3] * tx + mData[1 * 4 + 3]
                * ty + mData[2 * 4 + 3] * tz );
        return this;
    }

    public Matrix4f inverse () {
        Matrix4f m = new Matrix4f(get());
        m.invert();
        return m;
    }

    public String toString() {
        String string = new String();

        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++)
                string += get(i,j) + " ";
            string += '\n';
        }

        return string;
    }


    public static Matrix4f loadIdentity() {
        return new Matrix4f();
    }

    public static Matrix4f ortho(float left, float right, float top, float bottom, float nearZ, float farZ) {
        float deltaX = right - left;
        float deltaY = top - bottom;
        float deltaZ = farZ - nearZ;
        float[] orthoMat = loadIdentity().get();

        if ( ( deltaX == 0.0f ) || ( deltaY == 0.0f ) || ( deltaZ == 0.0f ) ) {
            return null;
        }

        orthoMat[0 * 4 + 0] = 2.0f / deltaX;
        orthoMat[3 * 4 + 0] = - ( right + left ) / deltaX;
        orthoMat[1 * 4 + 1] = 2.0f / deltaY;
        orthoMat[3 * 4 + 1] = - ( top + bottom ) / deltaY;
        orthoMat[2 * 4 + 2] = -2.0f / deltaZ;
        orthoMat[3 * 4 + 2] = - ( nearZ + farZ ) / deltaZ;

        return new Matrix4f(orthoMat);
    }


    private static float[] matrixMultiply ( float[] srcA, float[] srcB ) {
        float[] tmp = new float[16];
        int i;

        for ( i = 0; i < 4; i++ )
        {
            tmp[i * 4 + 0] = ( srcA[i * 4 + 0] * srcB[0 * 4 + 0] )
                    + ( srcA[i * 4 + 1] * srcB[1 * 4 + 0] )
                    + ( srcA[i * 4 + 2] * srcB[2 * 4 + 0] )
                    + ( srcA[i * 4 + 3] * srcB[3 * 4 + 0] );

            tmp[i * 4 + 1] = ( srcA[i * 4 + 0] * srcB[0 * 4 + 1] )
                    + ( srcA[i * 4 + 1] * srcB[1 * 4 + 1] )
                    + ( srcA[i * 4 + 2] * srcB[2 * 4 + 1] )
                    + ( srcA[i * 4 + 3] * srcB[3 * 4 + 1] );

            tmp[i * 4 + 2] = ( srcA[i * 4 + 0] * srcB[0 * 4 + 2] )
                    + ( srcA[i * 4 + 1] * srcB[1 * 4 + 2] )
                    + ( srcA[i * 4 + 2] * srcB[2 * 4 + 2] )
                    + ( srcA[i * 4 + 3] * srcB[3 * 4 + 2] );

            tmp[i * 4 + 3] = ( srcA[i * 4 + 0] * srcB[0 * 4 + 3] )
                    + ( srcA[i * 4 + 1] * srcB[1 * 4 + 3] )
                    + ( srcA[i * 4 + 2] * srcB[2 * 4 + 3] )
                    + ( srcA[i * 4 + 3] * srcB[3 * 4 + 3] );
        }

        return tmp;
    }


    public void scale(Vector3f s) {
        scale(s.x(),s.y(),s.z());
    }

    public void translate(Vector3f t) {
        translate(t.x(),t.y(),t.z());
    }

    public static Matrix4f lookAt( Vector3f eye, Vector3f center, Vector3f up ) {
        float[] mat = new float[16];
        Matrix.setLookAtM( mat, 0,
                eye.x(), eye.y(), eye.z(),
                center.x(), center.y(), center.z(),
                up.x(), up.y(), up.z()
        );
        return new Matrix4f(mat);
    }

    public Matrix4f rotate(float angle, Vector3f vec ){
        float[] data = new float[16];
        Matrix.setRotateM(data, 0, angle, vec.x(), vec.y(), vec.z());
        return this.multiply(new Matrix4f(data));
    }



}
