package com.example.a3dge_test;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.sevixoo.android3dge.GLContext;
import com.sevixoo.android3dge.Loader;
import com.sevixoo.android3dge.Object3D;
import com.sevixoo.android3dge.Scene;
import com.sevixoo.android3dge.WavefrontObject;
import com.sevixoo.android3dge.android.AndroidContext;
import com.sevixoo.android3dge.math.Vector3f;

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

    private WavefrontObject mSquareObject;

    public TestRenderer(Context mContext, GLSurfaceView surfaceView) {
        this.mContext = mContext;
        this.mGLSurfaceView = surfaceView;
        this.mScene = new Scene();
        this.mLoader = new Loader(new AndroidContext(mContext));
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLContext.initialize();

        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        this.mSquareObject = new WavefrontObject(new float[]{
                /*-0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f*/

                // front
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                // back
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,


        }, new short[]{
                // front
                0, 1, 2,
                2, 3, 0,
                // top
                1, 5, 6,
                6, 2, 1,
                // back
                7, 6, 5,
                5, 4, 7,
                // bottom
                4, 0, 3,
                3, 7, 4,
                // left
                4, 5, 1,
                1, 0, 4,
                // right
                3, 2, 6,
                6, 7, 3,
        });

        mScene.addObject(mSquareObject);
        //mSquareObject.scale(2.0f);
        //mSquareObject.translate(new Vector3f(1.25f,0.0f,0.0f));
        mSquareObject.rotateX(33);
        mSquareObject.rotateY(45);


        mSquareObject.setDrawLines(true);
        mSquareObject.setDrawTriangles(false);
        mSquareObject.setDrawPoints(true);

        try {
            this.mSquareObject.setShader(mLoader.loadShader("default"));
            this.mSquareObject.setWavefrontShader(mLoader.loadShader("color"));
            this.mSquareObject.setPointsShader(mLoader.loadShader("color"));
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);

        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mScene.viewport(width,height);
        float a = (float) width / (float) height;
        float s = 10;
        mScene.getCamera().orthogonalProjection(-s*a,s*a,-s*1,s*1,-s*1,s*1);
        mScene.getCamera().setPosition(new Vector3f(0.0f,0.0f,0.1f));
        mScene.getCamera().lookAt(new Vector3f(0.0f,0,0),new Vector3f(0f, 1.0f, 0.0f));
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        mScene.clearColor();
        mScene.display();
    }
}
