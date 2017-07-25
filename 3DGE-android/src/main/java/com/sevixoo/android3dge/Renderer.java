package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Matrix4f;

/**
 * Created by seweryn on 25.07.2017.
 */

class Renderer {

    private Matrix4f mViewMatrix;
    private Matrix4f mProjectionMatrix;

    Renderer() { }

    void prepare(Matrix4f viewMatrix, Matrix4f projectionMatrix){
        mViewMatrix = viewMatrix;
        mProjectionMatrix = projectionMatrix;
    }

    void draw(Mesh mesh, ShaderProgram shaderProgram, Matrix4f mModelMatrix){
        GLContext gl = GLContext.get();
        gl.bindVertexArray(mesh.getVAOId());
        gl.bindElementsArrayBuffer(mesh.getIndexVBO());
        gl.enableVertexAttribArray(ShaderAttribute.VERTICES);

        if(shaderProgram != null && mModelMatrix != null) {
            Matrix4f mvpMatrix = mModelMatrix.multiply(mViewMatrix).multiply(mProjectionMatrix);
            shaderProgram.uniformMatrix4f(ShaderUniform.MVP_MATRIX, mvpMatrix);
        }

        gl.drawTriangleElements(mesh.getVerticesCount());

        gl.disableVertexAttribArray(ShaderAttribute.VERTICES);
        gl.bindElementsArrayBuffer(0);
        gl.bindVertexArray(0);
    }

    void draw(Mesh mesh){
        draw(mesh,null,null);
    }

}
