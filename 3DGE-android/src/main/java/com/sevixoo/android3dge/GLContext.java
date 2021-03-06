package com.sevixoo.android3dge;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by seweryn on 24.07.2017.
 */

public class GLContext {



    private static GLContext instance;

    public static GLContext get()throws RuntimeException{
        if(instance == null){
            throw new RuntimeException(  );
        }
        return instance;
    }

    public static void initialize() {
        Log.i("GL_VERSION", GLES30.glGetString(GLES30.GL_VERSION) );
        Log.i("GL_EXTENSIONS", GLES30.glGetString(GLES30.GL_EXTENSIONS) );
        instance = new GLContext();
    }

    public static void destroy() {
        instance = null;
    }

    private GLContext(){ }

    public int createVertexShader(String source){
        int shader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(shader, source);
        return shader;
    }

    public int createFragmentShader(String source){
        int shader = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(shader, source);
        return shader;
    }

    public String compileShader(int shader) {
        GLES30.glCompileShader(shader);
        IntBuffer status = IntBuffer.allocate(1);
        GLES30.glGetShaderiv( shader, GLES30.GL_COMPILE_STATUS , status );
        int errorCode = status.get();
        if(errorCode != GLES30.GL_TRUE){
            return GLES30.glGetShaderInfoLog( shader );
        }
        return null;
    }

    public void deleteShader(int shader){
        GLES30.glDeleteShader(shader);
    }

    public void useProgram(int program) {
        GLES30.glUseProgram(program);
    }

    public void bindAttribLocation(int program, int attributeName, String name) {
        GLES30.glEnableVertexAttribArray(attributeName);
        GLES30.glBindAttribLocation(program,attributeName,name);
        GLES30.glDisableVertexAttribArray(attributeName);
    }

    public int createProgram(int vertexShader, int fragmentShader) {
        int program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        return program;
    }

    public String compileProgram(int program){
        GLES30.glLinkProgram(program);
        IntBuffer status = IntBuffer.allocate(1);
        GLES30.glGetProgramiv( program, GLES30.GL_LINK_STATUS , status );
        int errorCode = status.get();
        if(errorCode == GLES30.GL_FALSE){
            return GLES30.glGetProgramInfoLog( program );
        }
        GLES30.glValidateProgram(program);
        status = IntBuffer.allocate(1);
        GLES30.glGetProgramiv( program, GLES30.GL_VALIDATE_STATUS , status );
        errorCode = status.get();
        if(errorCode == GLES30.GL_FALSE){
            return GLES30.glGetProgramInfoLog( program );
        }
        return null;
    }

    public void viewport(int x, int y, int width, int height) {
        GLES30.glViewport(x, y, width, height);
    }

    public void clearColor(float r, float g, float b, int a) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT|GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glClearColor(r, g, b, a);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    }

    public int genVertexArray() {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays( 1, intBuffer );
        return intBuffer.get();
    }

    public void bindVertexArray(int vao) {
        GLES30.glBindVertexArray(vao);
    }

    public int genBuffer() {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        GLES30.glGenBuffers( 1, intBuffer);
        return intBuffer.get();
    }

    public void bindArrayBuffer(int vbo) {
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vbo);
    }

    public void bindElementsArrayBuffer(int vbo) {
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,vbo);
    }

    public IntBuffer arrayBufferDataWrite(int[] data) {
        IntBuffer buffer = IntBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, data.length , buffer, GLES30.GL_STATIC_DRAW);
        return buffer;
    }

    public void elementsArrayBufferDataWrite(short[] data) {
        ShortBuffer buffer = ByteBuffer.allocateDirect ( data.length * 2 ).order ( ByteOrder.nativeOrder() ).asShortBuffer();
        buffer.put ( data ).position ( 0 );
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, data.length * 2 , buffer, GLES30.GL_STATIC_DRAW);
    }

    public void arrayBufferDataWrite(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect ( data.length * 4 )
                .order ( ByteOrder.nativeOrder() ).asFloatBuffer();
        buffer.put ( data ).position ( 0 );
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, data.length * 4 , buffer, GLES30.GL_STATIC_DRAW);
    }

    public void vertexAttribPointerF(int attributeNumber, int dataSize) {
        GLES30.glVertexAttribPointer(attributeNumber,dataSize,GLES30.GL_FLOAT,false,0,0);
    }

    public void vertexAttribPointerU(int attributeNumber, int dataSize) {
        GLES30.glVertexAttribPointer(attributeNumber,dataSize,GLES30.GL_UNSIGNED_INT,false,0,0);
    }

    public void drawTriangleElements( int verticesCount ) {
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, verticesCount, GLES30.GL_UNSIGNED_SHORT, 0);
    }

    public void drawPointsElements( int verticesCount ) {
        GLES30.glDrawElements(GLES30.GL_POINTS, verticesCount, GLES30.GL_UNSIGNED_SHORT, 0);
    }

    public void drawLinesElements( int verticesCount ) {
        GLES30.glDrawElements(GLES30.GL_LINE_STRIP, verticesCount, GLES30.GL_UNSIGNED_SHORT, 0);
    }

    public void enableVertexAttribArray(int index) {
        GLES30.glEnableVertexAttribArray(index);
    }

    public void disableVertexAttribArray(int index) {
        GLES30.glDisableVertexAttribArray(index);
    }

    public void uniform4f(int handle, float x, float y, float z, float w) {
        GLES30.glUniform4f(handle , x, y, z, w);
    }

    public void uniformMatrix4fv(int handle, float[] matrix) {
        FloatBuffer buffer = ByteBuffer.allocateDirect ( matrix.length * 4 ).order ( ByteOrder.nativeOrder() ).asFloatBuffer();
        buffer.put ( matrix ).position ( 0 );
        GLES30.glUniformMatrix4fv(handle, 1, false, buffer);
    }

    public int getUniformLocation(int program, String name) {
        return GLES30.glGetUniformLocation (program, name);
    }

    public void uniform1f(int handle, float value) {
        GLES30.glUniform1f(handle , value);
    }

    public void lineWidth(float width){
        GLES30.glLineWidth(width);
    }

    public void uniform1i(int handle, int value) {
        GLES30.glUniform1i(handle,value);
    }

    public void activeTexture2D( int textureNum , int textureId ){
        int glTexture = 0;
        switch (textureNum){
            case 0: glTexture = GLES30.GL_TEXTURE0;break;
            case 1: glTexture = GLES30.GL_TEXTURE1;break;
            case 2: glTexture = GLES30.GL_TEXTURE2;break;
            case 3: glTexture = GLES30.GL_TEXTURE3;break;
            case 4: glTexture = GLES30.GL_TEXTURE4;break;
            case 5: glTexture = GLES30.GL_TEXTURE5;break;
            case 6: glTexture = GLES30.GL_TEXTURE6;break;
            case 7: glTexture = GLES30.GL_TEXTURE7;break;
            case 8: glTexture = GLES30.GL_TEXTURE8;break;
        }
        GLES30.glActiveTexture ( glTexture );
        GLES30.glBindTexture ( GLES30.GL_TEXTURE_2D, textureId );
    }

    public static final int TEXTURE_WRAP_REPEAT = 1;
    public static final int TEXTURE_WRAP_CLAMP_TO_EDGE = 2;
    public static final int TEXTURE_WRAP_MIRRORED_REPEAT = 3;

    private int wrapType(int type){
        switch (type){
            case TEXTURE_WRAP_REPEAT:return GLES30.GL_REPEAT;
            case TEXTURE_WRAP_CLAMP_TO_EDGE:return GLES30.GL_CLAMP_TO_EDGE;
            case TEXTURE_WRAP_MIRRORED_REPEAT:return GLES30.GL_MIRRORED_REPEAT;
        }
        return 0;
    }

    public void textParameterWrap_S( int type ){
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, wrapType(type) );
    }

    public void textParameterWrap_T( int type ){
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, wrapType(type) );
    }


}
