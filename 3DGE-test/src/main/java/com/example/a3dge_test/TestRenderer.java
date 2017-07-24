package com.example.a3dge_test;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sevixoo.android3dge.GLContext;
import com.sevixoo.android3dge.Loader;
import com.sevixoo.android3dge.Object3D;
import com.sevixoo.android3dge.Scene;
import com.sevixoo.android3dge.android.AndroidContext;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by seweryn on 22.07.2017.
 */
public class TestRenderer implements GLSurfaceView.Renderer{

    private Context mContext;
    private Loader mLoader;
    private GLSurfaceView mGLSurfaceView;

    private Scene mScene;

    private Object3D mSquareObject;


    public TestRenderer(Context mContext, GLSurfaceView surfaceView) {
        this.mContext = mContext;
        this.mGLSurfaceView = surfaceView;
        this.mScene = new Scene();
        this.mLoader = new Loader(new AndroidContext(mContext));
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLContext.initialize();
        Log.e("onSurfaceCreated","onSurfaceCreated");

        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        this.mSquareObject = new Object3D(new float[]{
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f
        }, new int[]{ 0, 1, 2, 0, 2, 3 });

        mScene.addObject(mSquareObject);

        Log.e("onSurfaceCreated","go");
        try {
            this.mSquareObject.setShader(mLoader.loadShader("def"));
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e("onSurfaceCreated","error");
            throw new RuntimeException(ex);

        }
        Log.e("onSurfaceCreated","end");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        mScene.viewport(w,h);
        Log.e("onSurfaceChanged","viewport");
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        mScene.clearColor();
        mScene.display();
    }
}
