package com.sevixoo.android3dge;

import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by seweryn on 24.07.2017.
 */
public class Mesh {

    private int mIndexVBO;
    private int mPositionsVBO;

    private int mVAOId;
    private int mVerticesCount;

    Mesh(float[] vertices, int[] indices){
        GLContext gl = GLContext.get();
        mVerticesCount = indices.length;

        mVAOId = gl.genVertexArray();
        gl.bindVertexArray(mVAOId);

            mIndexVBO = gl.genBuffer();
            gl.bindArrayBuffer(mIndexVBO);
                gl.arrayBufferDataWrite(indices);
                gl.vertexAttribPointerU(0, 2);
            gl.bindArrayBuffer(0);

            mPositionsVBO = gl.genBuffer();
            gl.bindArrayBuffer(mPositionsVBO);
                gl.arrayBufferDataWrite(vertices);
                gl.vertexAttribPointerF(1, 3);
            gl.bindArrayBuffer(0);

        gl.bindVertexArray(0);
    }

    void draw() {
        GLContext gl = GLContext.get();
        gl.bindVertexArray(mVAOId);
            gl.enableVertexAttribArray(0);
            //gl.enableVertexAttribArray(1);
                gl.drawTriangleElements(mVerticesCount);
            gl.disableVertexAttribArray(1);
            //gl.disableVertexAttribArray(0);
        gl.bindVertexArray(0);
    }
}
