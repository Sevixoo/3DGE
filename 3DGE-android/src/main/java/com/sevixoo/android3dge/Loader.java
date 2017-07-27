package com.sevixoo.android3dge;

import com.sevixoo.android3dge.system.SystemContext;

import java.io.IOException;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Loader {

    private SystemContext mContext;

    public Loader(SystemContext context) {
        this.mContext = context;
    }

    public ShaderProgram loadShader( String vertexFileName, String fragmentFileName )throws IOException{
        return new ShaderProgram(
                new Shader( ShaderType.VERTEX_SHADER, mContext.loadFile( vertexFileName ) ),
                new Shader( ShaderType.FRAGMENT_SHADER, mContext.loadFile( fragmentFileName ) )
        );
    }

    public ShaderProgram loadShader( String shaderName )throws IOException{
        return loadShader( shaderName + "_vert.glsl", shaderName + "_frag.glsl" );
    }

    public Texture loadTexture( String fileName ){

        //GLES30.glTexImage2D();


        return null;
    }

    public Object3D loadObjFile( String fileName ){
        return null;
    }

}
