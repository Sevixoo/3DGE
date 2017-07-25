package com.sevixoo.android3dge;

import android.opengl.GLES30;

import com.sevixoo.android3dge.math.Matrix4f;
import com.sevixoo.android3dge.math.Vector4f;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Object3D {

    private ShaderProgram   mShader;
    private Texture         mTexture;
    private Mesh            mMesh;

    private Vector4f        mColor;

    private Matrix4f        mModelMatrix;

    //position, rotation, scale

    //collider

    public Object3D(float[] vertices, short[] indices){
        this(new Mesh(vertices,indices),null,null);
        mModelMatrix = Matrix4f.loadIdentity();
        mColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private Object3D(Mesh mesh, ShaderProgram shaderProgram, Texture texture){
        this.mMesh = mesh;
        this.mTexture = texture;
        this.mShader = shaderProgram;
    }

    public void setShader(ShaderProgram mShader){
        this.mShader = mShader;
    }

    public void setColor(Vector4f color){
        mColor = color;
    }

    public void scale( float s ){
        scale(s,s,s);
    }

    public void scale( float sx, float sy, float sz ){
        mModelMatrix.scale(sz, sy, sz);
    }

    public void translate( float dx, float dy, float dz ){
        mModelMatrix.translate(dx, dy, dz);
    }

    void draw(Renderer renderer) {
        mShader.start();
        mShader.uniformVec4f(ShaderUniform.COLOR, mColor);
        renderer.draw(mMesh, mShader, mModelMatrix);
        mShader.stop();
    }
}
