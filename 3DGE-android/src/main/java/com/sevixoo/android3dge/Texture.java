package com.sevixoo.android3dge;

import android.opengl.GLES30;

import java.nio.ByteBuffer;

/**
 * Created by seweryn on 24.07.2017.
 */

public class Texture {

    public static final int TEXTURE_0 = 0;
    public static final int TEXTURE_1 = 1;
    public static final int TEXTURE_2 = 2;
    public static final int TEXTURE_3 = 3;
    public static final int TEXTURE_4 = 4;
    public static final int TEXTURE_5 = 5;
    public static final int TEXTURE_6 = 6;
    public static final int TEXTURE_7 = 7;
    public static final int TEXTURE_8 = 8;

    public static final int FILTER_NEAREST = 1;
    public static final int FILTER_LINEAR = 2;

    private int mTextureId;

    public Texture(byte[] pixels, int width, int height){
        int[] textureId = new int[1];
        ByteBuffer pixelBuffer = ByteBuffer.allocateDirect ( width * height * 3 );
        pixelBuffer.put ( pixels ).position ( 0 );

        // Use tightly packed data
        GLES30.glPixelStorei ( GLES30.GL_UNPACK_ALIGNMENT, 1 );

        //  Generate a texture object
        GLES30.glGenTextures ( 1, textureId, 0 );

        // Bind the texture object
        GLES30.glBindTexture ( GLES30.GL_TEXTURE_2D, textureId[0] );

        //  Load the texture
        GLES30.glTexImage2D ( GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGB, width, height, 0, GLES30.GL_RGB, GLES30.GL_UNSIGNED_BYTE, pixelBuffer );

        // Set the filtering mode
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST );
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST );

        mTextureId = textureId[0];
    }

    public void bind(int textureNum) {
        GLContext gl = GLContext.get();
        gl.activeTexture2D( textureNum , mTextureId );
    }

    private int getFilterType(int type){
        switch (type){
            case FILTER_LINEAR:return GLES30.GL_LINEAR;
            case FILTER_NEAREST:return GLES30.GL_NEAREST;
        }
        return 0;
    }

    public void setMagFilter( int filterType ){
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, getFilterType(filterType) );
    }

    public void setMinFilter( int filterType ){
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, getFilterType(filterType) );
    }


}
