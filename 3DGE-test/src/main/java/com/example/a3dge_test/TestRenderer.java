package com.example.a3dge_test;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.sevixoo.android3dge.debug.CameraManipulator;
import com.sevixoo.android3dge.system.Device;
import com.sevixoo.android3dge.GLContext;
import com.sevixoo.android3dge.Loader;
import com.sevixoo.android3dge.debug.ObjectManipulator;
import com.sevixoo.android3dge.Scene;
import com.sevixoo.android3dge.debug.WavefrontObject;
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
    private ObjectManipulator mObjectManipulator;
    private CameraManipulator mCameraManipulator;

    public TestRenderer(Context applicationContext, GLSurfaceView surfaceView, ObjectManipulator mObjectManipulator, CameraManipulator cameraManipulator) {
        this.mContext = applicationContext;
        this.mGLSurfaceView = surfaceView;
        this.mScene = new Scene();
        this.mLoader = new Loader(new AndroidContext(mContext));
        this.mObjectManipulator = mObjectManipulator;
        this.mCameraManipulator = cameraManipulator;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLContext.initialize();

        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        //this.mSquareObject = new WavefrontObject( mSquareVertices, mTextureMapping, mSquareIndices );
        this.mSquareObject = new WavefrontObject( mCubeVertices, mTextureMapping, mCubeIndices );

        mScene.addObject(mSquareObject);
        mSquareObject.scale(0.6f);
        //mSquareObject.translate(new Vector3f(1.25f,0.0f,0.0f));
        //mSquareObject.rotateX(33);
        //mSquareObject.rotateY(45);

        mSquareObject.setDrawLines(true);
        mSquareObject.setDrawTriangles(false);
        mSquareObject.setDrawPoints(true);

        mSquareObject.setPointsSize(15f);

        try {
            this.mSquareObject.setShader(mLoader.loadShader("color"));
            this.mSquareObject.setWavefrontShader(mLoader.loadShader("color"));
            this.mSquareObject.setPointsShader(mLoader.loadShader("color"));
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        mObjectManipulator.setTarget(mSquareObject);

        /*Texture mTexture = new Texture(mSampleTexture, 2, 2);
        mTexture.setMagFilter(Texture.FILTER_NEAREST);
        mTexture.setMinFilter(Texture.FILTER_NEAREST);
        mSquareObject.setTexture(mTexture);*/
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Device.mHeight = height;
        Device.mWidth = width;
        mScene.viewport(width,height);
        float a = (float) width / (float) height;
        float s = 2;
        mScene.getCamera().orthogonalProjection(-s*a,s*a,-s*1,s*1,-s*1,s*1);
        mScene.getCamera().setPosition(new Vector3f(0.0f,0.0f,0.1f));
        mScene.getCamera().lookAt(new Vector3f(0.0f,0,0),new Vector3f(0f, 1.0f, 0.0f));
        mCameraManipulator.setTarget(mScene.getCamera());
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        mObjectManipulator.onUpdate();
        mCameraManipulator.onUpdate();
        mScene.clearColor();
        mScene.display();
    }


    private final byte[] mSampleTexture = new byte[]{
                0, ( byte ) 0xff,   0, // Green
                ( byte ) 0xff,   0,   0, // Red
                0,   0, ( byte ) 0xff, // Blue
                ( byte ) 0xff, ( byte ) 0xff,   0 // Yellow
    };

    private final float[] mTextureMapping = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
    };

    private final float[] mSquareVertices = new float[]{
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f
    };

    private final short[] mSquareIndices = new short[]{
            0, 1, 2,
            0, 2, 3
    };

    private final float[] mCubeVertices = new float[]{
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
    };

    private final short[] mCubeIndices = new short[]{
        0, 1, 2,
        2, 3, 0,
        1, 5, 6,
        6, 2, 1,
        7, 6, 5,
        5, 4, 7,
        4, 0, 3,
        3, 7, 4,
        4, 5, 1,
        1, 0, 4,
        3, 2, 6,
        6, 7, 3,
    };

}
