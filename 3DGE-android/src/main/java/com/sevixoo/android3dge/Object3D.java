package com.sevixoo.android3dge;

import android.opengl.GLES30;

import com.sevixoo.android3dge.math.Matrix4f;
import com.sevixoo.android3dge.math.Vector3f;
import com.sevixoo.android3dge.math.Vector4f;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Object3D {

    private ShaderProgram   mShader;
    private Texture         mTexture;
    private Mesh            mMesh;

    private Vector4f        mColor;

    private Vector3f        mScale;
    private Vector3f        mTranslation;
    private Vector3f        mRotation;

    //collider

    public Object3D(float[] vertices, short[] indices){
        this(new Mesh(vertices,indices),null,null);
        mColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        mTranslation = new Vector3f(0, 0, 0);
        mScale = new Vector3f(1, 1, 1);
        mRotation = new Vector3f(0, 0, 0);
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
        scale(new Vector3f(s,s,s));
    }

    public void scale( Vector3f vector3f ){
        mScale = vector3f;
    }

    public void translate( Vector3f vector3f ){
        mTranslation = vector3f;
    }

    public void rotateX( float angle ){
        mRotation = new Vector3f(angle, mRotation.y(), mRotation.z());
    }

    public void rotateY( float angle ){
        mRotation = new Vector3f(mRotation.x(), angle, mRotation.z());
    }

    public void rotateZ( float angle ){
        mRotation = new Vector3f(mRotation.x(), mRotation.y(), angle);
    }

    protected Mesh getMesh(){
        return mMesh;
    }

    protected Matrix4f getModelMatrix(){
        Matrix4f modelMatrix = Matrix4f.loadIdentity();
        modelMatrix.scale(mScale);
        modelMatrix.translate(mTranslation);
        modelMatrix = modelMatrix.rotate(mRotation.x(),new Vector3f(-1.0f,0.0f,0.0f));
        modelMatrix = modelMatrix.rotate(mRotation.y(),new Vector3f(0.0f,-1.0f,0.0f));
        modelMatrix = modelMatrix.rotate(mRotation.z(),new Vector3f(0.0f,0.0f,-1.0f));
        return modelMatrix;
    }

    void draw(Renderer renderer) {
        mShader.start();
        mShader.uniformVec4f(ShaderUniform.COLOR, mColor);
        renderer.drawTriangles(mMesh, mShader, getModelMatrix());
        mShader.stop();
    }
}
