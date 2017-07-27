package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Matrix4f;

/**
 * Created by seweryn on 25.07.2017.
 */

public class Renderer {

    private static final int DRAW_LINES = 2;
    private static final int DRAW_POINTS = 1;
    private static final int DRAW_TRIANGLES = 3;

    private Matrix4f mViewMatrix;
    private Matrix4f mProjectionMatrix;

    Renderer() { }

    void prepare(Matrix4f viewMatrix, Matrix4f projectionMatrix){
        mViewMatrix = viewMatrix;
        mProjectionMatrix = projectionMatrix;
    }

    private void draw(Mesh mesh, ShaderProgram shaderProgram, Matrix4f mModelMatrix, int drawType){
        GLContext gl = GLContext.get();
        gl.bindVertexArray(mesh.getVAOId());
        gl.bindElementsArrayBuffer(mesh.getIndexVBO());
        gl.enableVertexAttribArray(ShaderAttribute.VERTICES);
        gl.enableVertexAttribArray(ShaderAttribute.TEXTURE_CORDS);

        if(shaderProgram != null && mModelMatrix != null) {
            Matrix4f mvpMatrix = mModelMatrix.multiply(mViewMatrix).multiply(mProjectionMatrix);
            shaderProgram.uniformMatrix4f(ShaderUniform.MVP_MATRIX, mvpMatrix);
        }

        if(drawType == DRAW_POINTS){
            gl.drawPointsElements(mesh.getVerticesCount());
        }else if(drawType == DRAW_LINES){
            gl.drawLinesElements(mesh.getVerticesCount());
        }else if(drawType == DRAW_TRIANGLES){
            gl.drawTriangleElements(mesh.getVerticesCount());
        }

        gl.disableVertexAttribArray(ShaderAttribute.TEXTURE_CORDS);
        gl.disableVertexAttribArray(ShaderAttribute.VERTICES);
        gl.bindElementsArrayBuffer(0);
        gl.bindVertexArray(0);
    }

    public void drawTriangles(Mesh mesh, ShaderProgram shaderProgram, Matrix4f mModelMatrix){
        draw(mesh,shaderProgram,mModelMatrix,DRAW_TRIANGLES);
    }

    public void drawLines(Mesh mesh, ShaderProgram shaderProgram, Matrix4f mModelMatrix){
        draw(mesh,shaderProgram,mModelMatrix,DRAW_LINES);
    }

    public void drawPoints(Mesh mesh, ShaderProgram shaderProgram, Matrix4f mModelMatrix){
        draw(mesh,shaderProgram,mModelMatrix,DRAW_POINTS);
    }

    public void drawTriangles(Mesh mesh){
        drawTriangles(mesh,null,null);
    }



}
