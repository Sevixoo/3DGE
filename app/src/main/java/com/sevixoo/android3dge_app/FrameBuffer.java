package com.sevixoo.android3dge_app;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by pi19124 on 27.06.2017.
 */

public class FrameBuffer {

    private int mFrameBufferId;
    private int mRenderedTextureId;

    public FrameBuffer(int width, int height) {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        GLES20.glGenFramebuffers(1, intBuffer);
        mFrameBufferId = intBuffer.get();
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);

        IntBuffer textureBuffer = IntBuffer.allocate(1);
        GLES20.glGenTextures(1, textureBuffer);
        mRenderedTextureId = textureBuffer.get();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mRenderedTextureId);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, width, height, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mRenderedTextureId, 0);

        IntBuffer renderBuffer = IntBuffer.allocate(1);
        GLES20.glGenRenderbuffers(1, renderBuffer);
        int renderBuff = renderBuffer.get();

        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, renderBuff);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height);
        GLES20.glFramebufferRenderbuffer(
                GLES20.GL_FRAMEBUFFER,
                GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER,
                renderBuff);

        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE){
            throw new RuntimeException("ERROR::FRAMEBUFFER:: Framebuffer is not complete!");
        }
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    int getRenderedTexture(){
        return mRenderedTextureId;
    }

    void bind(){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);
    }

    void unbind(){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    void delete(){
        IntBuffer intBuffer = IntBuffer.allocate(1);
        intBuffer.put(mFrameBufferId);
        GLES20.glDeleteFramebuffers(1, intBuffer);
    }

}
