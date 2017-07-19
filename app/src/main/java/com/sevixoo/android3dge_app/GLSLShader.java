package com.sevixoo.android3dge_app;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pi19124 on 26.06.2017.
 */

public class GLSLShader {

    private int mProgram;

    private int mVertexShader;
    private int mFragmentShader;

    private List<Integer> mBoundVertexBuffers;

    public GLSLShader(String vertex, String fragment) {
        mVertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(mVertexShader, vertex);
        GLES20.glCompileShader(mVertexShader);

        mFragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(mFragmentShader, fragment);
        GLES20.glCompileShader(mFragmentShader);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, mVertexShader);
        GLES20.glAttachShader(mProgram, mFragmentShader);
        GLES20.glLinkProgram(mProgram);

        mBoundVertexBuffers = new ArrayList<>();
    }

    public void destroy(){
        GLES20.glDeleteProgram(mProgram);
        GLES20.glDeleteShader(mVertexShader);
        GLES20.glDeleteShader(mFragmentShader);
    }

    public void start(){
        GLES20.glUseProgram(mProgram);
    }

    public void stop(){
        GLES20.glUseProgram(0);
        for (int i = 0; i < mBoundVertexBuffers.size(); i++) {
            Integer vertexBuffer = mBoundVertexBuffers.get(i);
            GLES20.glDisableVertexAttribArray(vertexBuffer);
            mBoundVertexBuffers.remove(i);
        }
    }

    public void bindVertexBuffer(String name , FloatBuffer vertexBuffer){
        int mHandle = GLES20.glGetAttribLocation(mProgram, name);
        GLES20.glEnableVertexAttribArray(mHandle);
        GLES20.glVertexAttribPointer(mHandle, 3 , GLES20.GL_FLOAT, false, 12 , vertexBuffer);
        mBoundVertexBuffers.add(mHandle);
    }

    public void bindUniformTexture(String name ,int texture) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram,name), 0);
    }

    public void bindUniform1i(String name,int x) {
        GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram,name), x);
    }

    public void bindUniformMatrix4fv(String name, float[] matrix) {
        int mHandle = GLES20.glGetUniformLocation(mProgram,name);
        GLES20.glUniformMatrix4fv(mHandle, 1, false, matrix, 0);
    }

    public void bindUniform4fv(String name, float[] matrix) {
        int mHandle = GLES20.glGetUniformLocation(mProgram, name);
        GLES20.glUniform4fv(mHandle, 1, matrix, 0);
    }

    public void bindUniform3fv(String name, float[] matrix) {
        int mHandle = GLES20.glGetUniformLocation(mProgram, name);
        GLES20.glUniform3fv(mHandle, 1, matrix, 0);
    }
}
