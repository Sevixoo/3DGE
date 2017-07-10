package com.sevixoo.android3dge_app;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by pi19124 on 28.06.2017.
 */

public class ScreenRenderer {

    private static float mVertexes[] = {
            -1.0f,  1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f,  1.0f, 0.0f
    };

    private static float mTextureVertices[] = {
            1.0f,  1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f,  1.0f, 0.0f,
    };

    private short mDrawOrder[] = { 0, 1, 2, 0, 2, 3 };

    private FloatBuffer mTextureBuffer;
    private FloatBuffer mVertexBuffer;
    private ShortBuffer mDrawListBuffer;

    private GLSLShader mShader;

    public ScreenRenderer() {

        ByteBuffer bb = ByteBuffer.allocateDirect( mVertexes.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(mVertexes);
        mVertexBuffer.position(0);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(mTextureVertices.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        mTextureBuffer = bb2.asFloatBuffer();
        mTextureBuffer.put(mTextureVertices);
        mTextureBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect( mDrawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mDrawListBuffer = dlb.asShortBuffer();
        mDrawListBuffer.put(mDrawOrder);
        mDrawListBuffer.position(0);
    }

    public void clearColor(){
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_STENCIL_BUFFER_BIT);
    }

    public void setShader(GLSLShader shader){
        mShader = shader;
    }

    public void display(GLSLShader shader, FrameBuffer frameBuffer){
        mShader = shader;
        display(frameBuffer);
    }

    public void display(FrameBuffer frameBuffer){
        mShader.start();

        mShader.bindVertexBuffer("vPosition",mVertexBuffer);
        mShader.bindVertexBuffer("vTextureCoordinate",mTextureBuffer);
        mShader.bindUniformTexture("vScreenTexture",frameBuffer.getRenderedTexture());

        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, mDrawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer );

        mShader.stop();
    }

    public void draw(GLSLShader shader, FrameBuffer input,FrameBuffer output){
        mShader = shader;
        draw(input,output);
    }

    public void draw(FrameBuffer input,FrameBuffer output){
        output.bind();
        display(input);
        output.unbind();
    }

}
