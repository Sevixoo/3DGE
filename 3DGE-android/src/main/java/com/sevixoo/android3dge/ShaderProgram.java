package com.sevixoo.android3dge;

import java.io.IOException;
import java.nio.IntBuffer;

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

        this.mProgram = gl.createProgram( mVertexShader.getId(), mFragmentShader.getId() );
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

    public void bindAttribute( String name, int attributeName ){
        GLContext.get().bindAttribLocation( mProgram, attributeName, name );
    }

}
