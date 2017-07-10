package com.sevixoo.android3dge_app;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity {

    private GLSurfaceView mGlSurfaceView;

    private Object3D mObject3D;
    private Object3D mScaledObject3D;

    private Object3D mBananaObject;

    private Loader mLoader;

    private LinearLayout mContainer;
    private GLSLShader mDefGLSLShader;
    private GLSLShader mColorGLSLShader;
    private GLSLShader mRendererShader;
    private GLSLShader mGscaleShader;

    private World mWorld;
    private FrameBuffer mFrameBuffer;
    private FrameBuffer mFrameBuffer2;
    private ScreenRenderer mScreen;

    public void loadData(){
        mWorld = new World();

        /*mObject3D = new Object3D(new float[]{   // in counterclockwise order:
                1080.0f,  1704.0f, 0.0f, // top
                0.0f, 0.0f, 0.0f, // bottom left
                0.0f, 1704.0f, 0.0f, // bottom left
        });*/

        mObject3D = new Object3D(new float[]{   // in counterclockwise order:
                1080.0f,  1704.0f, 0.0f, // top
                0.0f, 0.0f, 0.0f, // bottom left
                //0.0f, 1704.0f, 0.0f, // bottom left
                1080.0f,  0.0f, 0.0f, // top

                1080.0f,  1704.0f, 0.0f, // top
                0.0f, 0.0f, 0.0f, // bottom left
                0.0f, 1704.0f, 0.0f, // bottom left
        });

        /*mObject3D = new Object3D(new float[]{   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        });*/
        mObject3D.setColor(new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        mScaledObject3D = new Object3D(new float[]{   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        });
        mScaledObject3D.setColor(new float[]{ 0.33671875f, 0.36953125f, 0.22265625f, 1.0f });
        mScaledObject3D.scale(1.5f);

        try {

            mBananaObject = mLoader.loadObj("cube2.obj");

            mDefGLSLShader = new GLSLShader(
                    mLoader.loadFile("def.vert"),
                    mLoader.loadFile("def.frag")
            );
            mColorGLSLShader = new GLSLShader(
                    mLoader.loadFile("def.vert"),
                    mLoader.loadFile("color.frag")
            );
            mRendererShader = new GLSLShader(
                    mLoader.loadFile("renderer.vert"),
                    mLoader.loadFile("renderer.frag")
            );
            mGscaleShader = new GLSLShader(
                    mLoader.loadFile("grey_scale.vert"),
                    mLoader.loadFile("grey_scale.frag")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBananaObject.setGLSLShader(mDefGLSLShader);
        mScaledObject3D.setGLSLShader(mColorGLSLShader);
        mObject3D.setGLSLShader(mDefGLSLShader);
        //mWorld.add(mScaledObject3D);
        //mWorld.add(mObject3D);
        mWorld.add(mBananaObject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContainer = (LinearLayout)findViewById(R.id.activity_main);
        mLoader = new Loader(this);
        onLoaded();
    }

    public void onLoaded(){
        mGlSurfaceView = new GLSurfaceView(getBaseContext());
        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                loadData();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                Log.e("onSurfaceChanged", "width=" + width + ",height=" + height);
                GLES20.glViewport(0, 0, width, height);
                mWorld.getCamera().setProjection(0,width,height,0);
                mScreen = new ScreenRenderer();
                mWorld.getCamera().setPosition(0,0,3);
                mWorld.getCamera().lookAt(0,0,0);
                mFrameBuffer = new FrameBuffer(width,height);
                mFrameBuffer2 = new FrameBuffer(width,height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                mScreen.clearColor();
                mWorld.display();
                //mWorld.draw(mFrameBuffer);
                //mScreen.draw(mRendererShader,mFrameBuffer,mFrameBuffer2);
                //mScreen.display(mRendererShader,mFrameBuffer2);
            }
        });
        mContainer.addView(mGlSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mGlSurfaceView!=null) {
            mGlSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mGlSurfaceView!=null) {
            mGlSurfaceView.onPause();
        }
    }

}
