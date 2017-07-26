package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Vector4f;

/**
 * Created by seweryn on 26.07.2017.
 *
 * This object is used to debug 3D scene
 *
 */
public class WavefrontObject extends Object3D {

    private boolean mDrawTriangles = true;
    private boolean mDrawLines = true;
    private boolean mDrawPoints = true;

    private ShaderProgram mWavefrontShader;
    private ShaderProgram mPointsShader;

    private float mPointSize;
    private Vector4f mPointColor;

    private float mLineSize;
    private Vector4f mLinesColor;

    public WavefrontObject(float[] vertices,float[] texture, short[] indices) {
        super(vertices,texture, indices);
        mPointSize = 8f;
        mPointColor = new Vector4f(1.0f,1.0f,1.0f,1.0f);
        mLineSize = 2;
        mLinesColor = new Vector4f(9.0f,9.0f,9.0f,1.0f);
    }

    public void setWavefrontShader(ShaderProgram program){
        mWavefrontShader = program;
    }

    public void setPointsShader(ShaderProgram program){
        mPointsShader = program;
    }

    @Override
    void draw(Renderer renderer) {
        GLContext gl = GLContext.get();

        if(mDrawTriangles) {
            super.draw(renderer);
        }
        if(mDrawLines){
            mWavefrontShader.start();
            mWavefrontShader.uniformVec4f(ShaderUniform.COLOR,mLinesColor);
            gl.lineWidth(mLineSize);
            renderer.drawLines(getMesh(), mWavefrontShader, getModelMatrix());
            mWavefrontShader.stop();
        }
        if(mDrawPoints){
            mPointsShader.start();
            mPointsShader.uniformVec4f(ShaderUniform.COLOR,mPointColor);
            mPointsShader.uniform1f(ShaderUniform.POINT_SIZE,mPointSize);
            renderer.drawPoints(getMesh(), mPointsShader, getModelMatrix());
            mPointsShader.stop();
        }
    }

    public void setDrawTriangles(boolean doDraw) {
        this.mDrawTriangles = doDraw;
    }

    public void setDrawLines(boolean doDraw) {
        this.mDrawLines = doDraw;
    }

    public void setDrawPoints(boolean doDraw) {
        this.mDrawPoints = doDraw;
    }

    public void setPointsSize(float pointSize){
        this.mPointSize = pointSize;
    }

    public void setLineSize(float lineSize){
        this.mLineSize = lineSize;
    }

    public void setPointColor(Vector4f color){
        this.mPointColor = color;
    }

    public void setLineColor(Vector4f color){
        this.mLinesColor = color;
    }

}
