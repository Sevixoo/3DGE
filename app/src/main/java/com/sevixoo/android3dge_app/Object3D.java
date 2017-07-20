package com.sevixoo.android3dge_app;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sevixoo.android3dge_app.collision.ICollidingBody;
import com.sevixoo.android3dge_app.collision.SphereCollidingBody;
import com.sevixoo.android3dge_app.collision.ValueProvider;
import com.sevixoo.android3dge_app.math.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by pi19124 on 26.06.2017.
 */

public class Object3D {

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mNormalsBuffer;

    private ICollidingBody mCollidingBody;

    float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    private float[] mModelMatrix = new float[]{
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    };

    private final int vertexCount;
    private GLSLShader mGLSLShader;

    private float mScale = 1;
    private float[] mTranslation = new float[]{ 0 , 0 , 0};

    private float[] mRotationMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };

    public Object3D(float[] vertexes) {
        this(vertexes,new float[0],new float[0]);
    }

    public Object3D(float[] verticesArray, float[] texturesArray, float[] normalsArray){
        vertexCount = verticesArray.length / 3;
        ByteBuffer bb = ByteBuffer.allocateDirect( verticesArray.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(verticesArray);
        mVertexBuffer.position(0);

        ByteBuffer normalBuffer = ByteBuffer.allocateDirect( normalsArray.length * 4);
        normalBuffer.order(ByteOrder.nativeOrder());
        mNormalsBuffer = normalBuffer.asFloatBuffer();
        mNormalsBuffer.put(normalsArray);
        mNormalsBuffer.position(0);

        mCollidingBody = new SphereCollidingBody(new ValueProvider<Vector3f>() {
            @Override
            public Vector3f get() {
                return position();
            }
        }, 2*0.1f );
    }

    public void scale( float s ){
        mScale = s;
        mModelMatrix = buildModelMatrix();
    }

    public void rotateZ( float angle ){
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);
        mModelMatrix = buildModelMatrix();
    }

    public void rotateX( float angle ){
        Matrix.setRotateM(mRotationMatrix, 0, angle, -1.0f, 0, 0);
        mModelMatrix = buildModelMatrix();
    }

    public void rotateY( float angle ){
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, -1.0f, 0);
        mModelMatrix = buildModelMatrix();
    }

    public void translate(float vx, float vy, float vz){
        mTranslation[0] = vx;
        mTranslation[1] = vy;
        mTranslation[2] = vz;
        mModelMatrix = buildModelMatrix();
    }

    public Vector3f position(){
        return new Vector3f( mTranslation );
    }

    private float[] buildModelMatrix(){
        float s = mScale;
        float[] t = mTranslation;
        float[] modelMatrix = new float[]{
                1*s, 0, 0, t[0],
                0, 1*s, 0, t[1],
                0, 0, 1*s, t[2],
                0, 0, 0, 1
        };
        float[] transformed = new float[16];
        //Matrix.multiplyMM(transformed, 0, modelMatrix, 0, mRotationMatrix, 0);
        return modelMatrix;
    }

    public void setColor(float[] color){
        this.color = color;
    }


    public void setColor(float r, float g, float b){
        setColor(new float[]{ r/255f, g/255f, b/255f, 1 });
    }

    public void setGLSLShader(GLSLShader mGLSLShader){
        this.mGLSLShader = mGLSLShader;
    }

    public ICollidingBody getCollidingBody(){
        return mCollidingBody;
    }

    public void draw(float[] vpMatrix,float[] cameraPosition){
        float[] mvpMatrix = new float[16];
        Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, mModelMatrix, 0);
        mGLSLShader.start();

        //mGLSLShader.bindVertexBuffer("vNormal",mNormalsBuffer);
        mGLSLShader.bindVertexBuffer("vPosition",mVertexBuffer);

        mGLSLShader.bindUniformMatrix4fv("uMVPMatrix",mvpMatrix);
        mGLSLShader.bindUniform4fv("vColor",color);
        mGLSLShader.bindUniform3fv("cameraPosition",cameraPosition);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        mGLSLShader.stop();
    }

}
