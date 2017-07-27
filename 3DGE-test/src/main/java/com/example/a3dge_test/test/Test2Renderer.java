package com.example.a3dge_test.test;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by seweryn on 24.07.2017.
 */

public class Test2Renderer implements GLSurfaceView.Renderer{

    final int VERTEX_POS_SIZE   = 3; // x, y and z

    final int VERTEX_POS_INDX   = 0;

    final int VERTEX_STRIDE     =  ( 4 * ( VERTEX_POS_SIZE  ) );

    private Context mContext;
    private GLSurfaceView mGLSurfaceView;

    public Test2Renderer(Context mContext, GLSurfaceView mGLSurfaceView) {
        this.mContext = mContext;
        this.mGLSurfaceView = mGLSurfaceView;
    }

    private float[] vertices = new float[]{
            0.0f,  0.5f, 0.0f,        // v0
            -0.5f, -0.5f, 0.0f,        // v1
            0.5f, -0.5f, 0.0f,        // v2
    };

    private short[] indices = new short[]{ 0, 1, 2 };

    private int verticesCount;
    private int program;
    private int VAO;
    private int indicesVBO;
    private int vertexVBO;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        verticesCount = indices.length;

        Log.e("GL_VERSION", GLES30.glGetString(GLES30.GL_VERSION) );
        Log.e("GL_EXTENSIONS", GLES30.glGetString(GLES30.GL_EXTENSIONS) );

        loadShader();

        int[] buffers = new int[2];
        GLES30.glGenBuffers( 2, buffers, 0);

        vertexVBO = buffers[0];

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vertexVBO);

            FloatBuffer mVertices = ByteBuffer.allocateDirect ( vertices.length * 4 )
                    .order ( ByteOrder.nativeOrder() ).asFloatBuffer();
            mVertices.put ( vertices ).position ( 0 );

            GLES30.glBufferData ( GLES30.GL_ARRAY_BUFFER, vertices.length * 4, mVertices, GLES30.GL_STATIC_DRAW );

        indicesVBO = buffers[1];

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,indicesVBO);

            ShortBuffer mIndices = ByteBuffer.allocateDirect ( indices.length * 2 ).order ( ByteOrder.nativeOrder() ).asShortBuffer();
            mIndices.put ( indices ).position ( 0 );

            GLES30.glBufferData ( GLES30.GL_ELEMENT_ARRAY_BUFFER, 2 * indices.length, mIndices, GLES30.GL_STATIC_DRAW );


        int[] vaos = new int[1];
        GLES30.glGenVertexArrays( 1, vaos, 0 );
        VAO = vaos[0];

        GLES30.glBindVertexArray(VAO);

            GLES30.glBindBuffer ( GLES30.GL_ARRAY_BUFFER, vertexVBO );
            GLES30.glBindBuffer ( GLES30.GL_ELEMENT_ARRAY_BUFFER, indicesVBO );

            GLES30.glEnableVertexAttribArray ( VERTEX_POS_INDX );

            GLES30.glVertexAttribPointer ( VERTEX_POS_INDX, VERTEX_POS_SIZE, GLES30.GL_FLOAT, false, 0, 0 );

        GLES30.glBindVertexArray(0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        //GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        GLES30.glUseProgram(program);
            GLES30.glBindVertexArray(VAO);
                GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,indicesVBO);
                GLES30.glEnableVertexAttribArray(0);
                    GLES30.glDrawElements(GLES30.GL_TRIANGLES, verticesCount, GLES30.GL_UNSIGNED_SHORT, 0);
                //GLES30.glDisableVertexAttribArray(0);
                //GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,0);
            GLES30.glBindVertexArray(0);
        GLES30.glUseProgram(0);
    }

    public String loadFile(String path) throws IOException {
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();
        try {
            reader = new BufferedReader( new InputStreamReader(mContext.getAssets().open(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                returnString.append(line);
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {}
            }
        }
        return returnString.toString();
    }

    private void loadShader(){

        try {
            String vertexShaderCode = "#version 310 es\n" + loadFile("default_vert.glsl");
            String fragmentShaderCode = "#version 310 es\n" + loadFile("default_frag.glsl");

            int vertexShader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
            GLES30.glShaderSource(vertexShader, vertexShaderCode);

            GLES30.glCompileShader(vertexShader);
            IntBuffer status = IntBuffer.allocate(1);
            GLES30.glGetShaderiv( vertexShader, GLES30.GL_COMPILE_STATUS , status );
            int errorCode = status.get();
            if(errorCode != GLES30.GL_TRUE){
                throw new RuntimeException( GLES30.glGetShaderInfoLog( vertexShader ) );
            }

            int fragmentShader = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
            GLES30.glShaderSource(fragmentShader, fragmentShaderCode);

            GLES30.glCompileShader(fragmentShader);
            status = IntBuffer.allocate(1);
            GLES30.glGetShaderiv( fragmentShader, GLES30.GL_COMPILE_STATUS , status );
            errorCode = status.get();
            if(errorCode != GLES30.GL_TRUE){
                throw new RuntimeException( GLES30.glGetShaderInfoLog( fragmentShader ) );
            }

            program = GLES30.glCreateProgram();
            GLES30.glAttachShader(program, vertexShader);
            GLES30.glAttachShader(program, fragmentShader);

            GLES30.glLinkProgram(program);
            status = IntBuffer.allocate(1);
            GLES30.glGetProgramiv( program, GLES30.GL_LINK_STATUS , status );
            errorCode = status.get();
            if(errorCode == GLES30.GL_FALSE){
                throw new RuntimeException(  GLES30.glGetProgramInfoLog( program ));
            }
            GLES30.glValidateProgram(program);
            status = IntBuffer.allocate(1);
            GLES30.glGetProgramiv( program, GLES30.GL_VALIDATE_STATUS , status );
            errorCode = status.get();
            if(errorCode == GLES30.GL_FALSE){
                throw new RuntimeException(  GLES30.glGetProgramInfoLog( program ) );
            }

        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
