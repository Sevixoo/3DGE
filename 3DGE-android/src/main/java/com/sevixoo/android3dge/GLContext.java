package com.sevixoo.android3dge;

import android.opengl.GLES30;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.R.attr.data;

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
        GLES30.glBindAttribLocation(program,attributeName,name);
    }

    public int createProgram(int vertexShader, int fragmentShader) {
        int program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);
        return program;
    }

    public String compileProgram(int program){
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
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glClearColor(r, g, b, a);
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

    public void arrayBufferDataWrite(int[] data) {
        IntBuffer buffer = IntBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, data.length , buffer, GLES30.GL_STATIC_DRAW);
    }

    public void arrayBufferDataWrite(float[] data) {
        FloatBuffer buffer = FloatBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, data.length , buffer, GLES30.GL_STATIC_DRAW);
    }

    public void vertexAttribPointerF(int attributeNumber, int dataSize) {
        GLES30.glVertexAttribPointer(attributeNumber,dataSize,GLES30.GL_FLOAT,false,0,0);
    }

    public void vertexAttribPointerU(int attributeNumber, int dataSize) {
        GLES30.glVertexAttribPointer(attributeNumber,dataSize,GLES30.GL_UNSIGNED_INT,false,0,0);
    }

    public void drawTriangleElements( int verticesCount ) {
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, verticesCount, GLES30.GL_UNSIGNED_INT, 0);
    }

    public void enableVertexAttribArray(int index) {
        GLES30.glEnableVertexAttribArray(index);
    }

    public void disableVertexAttribArray(int index) {
        GLES30.glDisableVertexAttribArray(index);
    }
}
