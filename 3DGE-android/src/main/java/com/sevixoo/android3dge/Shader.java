package com.sevixoo.android3dge;

import java.io.IOException;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Shader {

    private int mShaderId;

    Shader(ShaderType type, String shaderCode) throws  IOException{
        GLContext gl = GLContext.get();

        if(type == ShaderType.VERTEX_SHADER){
            mShaderId = gl.createVertexShader(shaderCode);
        }else{
            mShaderId = gl.createFragmentShader(shaderCode);
        }
        String error = gl.compileShader(mShaderId);
        if(error != null){
            throw new IOException( "glCompileShader error infolog: " + error );
        }
    }

    public void destroy(){
        GLContext.get().deleteShader(mShaderId);
    }

    int getId() {
        return mShaderId;
    }
}
