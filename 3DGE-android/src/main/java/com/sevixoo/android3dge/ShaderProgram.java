package com.sevixoo.android3dge;

import com.sevixoo.android3dge.math.Matrix4f;
import com.sevixoo.android3dge.math.Vector4f;

import java.io.IOException;

/**
 * Created by seweryn on 22.07.2017.
 */
public class ShaderProgram {

    private int mProgram;

    private Shader mVertexShader;
    private Shader mFragmentShader;

    ShaderProgram(Shader mVertexShader, Shader mFragmentShader) throws IOException {
        this.mVertexShader = mVertexShader;
        this.mFragmentShader = mFragmentShader;

        GLContext gl = GLContext.get();

        mProgram = gl.createProgram( mVertexShader.getId(), mFragmentShader.getId() );

        String error = gl.compileProgram(mProgram);
        if(error != null){
            throw new IOException( "glValidateProgram error infolog: " + error );
        }
    }

    public void start(){
        GLContext.get().useProgram(mProgram);
    }

    public void stop(){
        GLContext.get().useProgram(0);
    }

    public void uniformVec4f(String name, Vector4f value) {
        int handle = GLContext.get().getUniformLocation(mProgram,name);
        GLContext.get().uniform4f(handle,value.x(),value.y(),value.z(),value.w());
    }

    public void uniform1f(String name, float value) {
        int handle = GLContext.get().getUniformLocation(mProgram,name);
        GLContext.get().uniform1f(handle,value);
    }

    public void uniformMatrix4f(String name, Matrix4f matrix) {
        int handle = GLContext.get().getUniformLocation(mProgram,name);
        GLContext.get().uniformMatrix4fv(handle,matrix.get());
    }
}
