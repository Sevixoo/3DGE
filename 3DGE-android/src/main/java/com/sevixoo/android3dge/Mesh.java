package com.sevixoo.android3dge;

/**
 * Created by seweryn on 24.07.2017.
 *
 * Mesh - stores vertices and indices data in VAO
 *
 */
class Mesh {

    private int mIndexVBO;
    private int mPositionsVBO;

    private int mVAOId;
    private int mVerticesCount;

    Mesh(float[] vertices, short[] indices){
        GLContext gl = GLContext.get();
        mVerticesCount = indices.length;

        mVAOId = gl.genVertexArray();
        gl.bindVertexArray(mVAOId);

            mIndexVBO = gl.genBuffer();
            gl.bindElementsArrayBuffer(mIndexVBO);
                gl.elementsArrayBufferDataWrite(indices);
            gl.bindElementsArrayBuffer(0);

            mPositionsVBO = gl.genBuffer();
            gl.bindArrayBuffer(mPositionsVBO);
                gl.arrayBufferDataWrite(vertices);
                gl.vertexAttribPointerF(ShaderAttribute.VERTICES, 3);
            gl.bindArrayBuffer(0);

        gl.bindVertexArray(0);
    }

    int getIndexVBO() {
        return mIndexVBO;
    }

    int getPositionsVBO() {
        return mPositionsVBO;
    }

    int getVAOId() {
        return mVAOId;
    }

    int getVerticesCount() {
        return mVerticesCount;
    }
}
