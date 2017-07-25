package com.sevixoo.android3dge;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Object3D {

    private ShaderProgram   mShader;
    private Texture         mTexture;
    private Mesh            mMesh;

    //position, rotation, scale

    //collider

    public Object3D(float[] vertices, short[] indices){
        this(new Mesh(vertices,indices),null,null);
    }

    Object3D(Mesh mesh, ShaderProgram shaderProgram, Texture texture){
        this.mMesh = mesh;
        this.mTexture = texture;
        this.mShader = shaderProgram;
    }

    public void setShader(ShaderProgram mShader){
        this.mShader = mShader;
    }

    public void draw() {
        mShader.start();
        mMesh.draw();
        mShader.stop();
    }
}
