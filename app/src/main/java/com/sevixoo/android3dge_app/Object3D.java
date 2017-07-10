package com.sevixoo.android3dge_app;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by pi19124 on 26.06.2017.
 */

public class Object3D {

    private FloatBuffer vertexBuffer;

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private float[] mModelMatrix = new float[]{
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    };

    private final int vertexCount;
    private GLSLShader mGLSLShader;

    private float mScale;

    public Object3D(float[] vertexes) {
        vertexCount = vertexes.length / 3;
        ByteBuffer bb = ByteBuffer.allocateDirect( vertexes.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexes);
        vertexBuffer.position(0);
    }

    public Object3D(float[] verticesArray, float[] texturesArray, float[] normalsArray) {
        vertexCount = verticesArray.length / 3;
        ByteBuffer bb = ByteBuffer.allocateDirect( verticesArray.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(verticesArray);
        vertexBuffer.position(0);
    }

    public void scale( float s ){
        mScale = s;
        buildModelMatrix();
    }

    private void buildModelMatrix(){
        float s = mScale;
        mModelMatrix = new float[]{
                1*s, 0, 0, 0,
                0, 1*s, 0, 0,
                0, 0, 1*s, 0,
                0, 0, 0, 1
        };
    }

    public void setColor(float[] color){
        this.color = color;
    }

    public void setGLSLShader(GLSLShader mGLSLShader){
        this.mGLSLShader = mGLSLShader;
    }

    public void draw(float[] vpMatrix){
        float[] mvpMatrix = new float[16];
        Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, mModelMatrix, 0);
        mGLSLShader.start();
        mGLSLShader.bindVertexBuffer("vPosition",vertexBuffer);
        mGLSLShader.bindUniformMatrix4fv("uMVPMatrix",mvpMatrix);
        mGLSLShader.bindUniform4fv("vColor",color);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        mGLSLShader.stop();
    }

}
