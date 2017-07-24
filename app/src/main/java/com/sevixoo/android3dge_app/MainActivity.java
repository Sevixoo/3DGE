package com.sevixoo.android3dge_app;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.sevixoo.android3dge_app.collision.WorldCollisionDetector;
import com.sevixoo.android3dge_app.math.Matrix4f;
import com.sevixoo.android3dge_app.math.Vector3f;
import com.sevixoo.android3dge_app.math.Vector4f;

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
    private GLSLShader mLightShader;

    private World mWorld;
    private FrameBuffer mFrameBuffer;
    private FrameBuffer mFrameBuffer2;
    private ScreenRenderer mScreen;

    private MotionEvent mMotionEvent;
    private WorldCollisionDetector mWorldCollisionDetector;

    public void loadData(){
        mWorld = new World();

        mObject3D = new Object3D(new float[]{   // in counterclockwise order:
                1080.0f,  1704.0f, 0.0f, // top
                0.0f, 0.0f, 0.0f, // bottom left
                //0.0f, 1704.0f, 0.0f, // bottom left
                1080.0f,  0.0f, 0.0f, // top

                1080.0f,  1704.0f, 0.0f, // top
                0.0f, 0.0f, 0.0f, // bottom left
                0.0f, 1704.0f, 0.0f, // bottom left
        });

        mObject3D.setColor(new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
        mScaledObject3D = new Object3D(new float[]{   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        });
        mScaledObject3D.setColor(new float[]{ 0.33671875f, 0.36953125f, 0.22265625f, 1.0f });
        mScaledObject3D.scale(1.5f);

        try {

            mBananaObject = mLoader.loadObj("holder.obj");

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
            mLightShader =  new GLSLShader(
                    mLoader.loadFile("light_shader.vert"),
                    mLoader.loadFile("light_shader.frag")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBananaObject.setColor(255,0,0);
        mBananaObject.rotateX(90f);
        mBananaObject.setGLSLShader(mDefGLSLShader);
        //mBananaObject.translate(1.5f,0,0);
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

            //mWorld.getCamera().setProjection(0,width,height,0);
            //float ratio = (float) width / height;
            //mWorld.getCamera().setProjection(-ratio,ratio,-1,1);

            mWorld.getCamera().setOrthoProjection(width,height,10);

            mScreen = new ScreenRenderer();
            mWorld.getCamera().setPosition(0,0,10);
            mWorld.getCamera().lookAt(0,0,0);

            mWorldCollisionDetector = new WorldCollisionDetector(mWorld.getCamera());

            mFrameBuffer = new FrameBuffer(width,height);
            mFrameBuffer2 = new FrameBuffer(width,height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            if(mMotionEvent != null) {
                //Normalised Device Coordinates
                float nx = (2.0f * mMotionEvent.getX()) / (float) mGlSurfaceView.getWidth() - 1.0f;
                float ny = 1.0f - (2.0f * mMotionEvent.getY()) / (float) mGlSurfaceView.getHeight();

                Vector4f rayClip = new Vector4f(nx, ny, -1.0f, 1.0f);
                //4d Eye (Camera) Coordinates
                Vector4f rayEye = new Matrix4f(mWorld.getCamera().getProjectionMatrix()).inverse().multiply(rayClip);
                rayEye = new Vector4f(rayEye.xy(), -1.0f, 0.0f);
                //World Coordinates
                Vector3f rayWor = new Matrix4f(mWorld.getCamera().getViewMatrix()).inverse().multiply(rayEye).xyz();
                rayWor = rayWor.normalize();


                Float collision = mBananaObject.getCollidingBody().test(mWorldCollisionDetector,rayWor);
                if(collision == null){
                    Log.e("miss rayWor", "wx:" + rayWor.x() + " wy:" + rayWor.y() + " wz:" + rayWor.z());
                    mBananaObject.setColor(0,0,255);
                }else{
                    Log.e("hit rayWor", "wx:" + rayWor.x() + " wy:" + rayWor.y() + " wz:" + rayWor.z());
                    mBananaObject.setColor(255,0,0);
                    Log.e("hit dist", "distance = " + collision);
                }
            }else{
                mBananaObject.setColor(0,0,255);
            }

            //long time = SystemClock.uptimeMillis() % 4000L;
            //float angle = 0.090f * ((int) time);
            //mBananaObject.rotateX(angle);

            mScreen.clearColor();
            mWorld.display();
            //mWorld.draw(mFrameBuffer);
            //mScreen.draw(mRendererShader,mFrameBuffer,mFrameBuffer2);
            //mScreen.display(mRendererShader,mFrameBuffer2);
        }
    });
        mContainer.addView(mGlSurfaceView);

        mGlSurfaceView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_DOWN){
                mMotionEvent = event;
            }else {
                mMotionEvent = null;
            }
            return true;
        }
    });

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
