package com.sevixoo.android3dge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seweryn on 22.07.2017.
 */
public class Scene {

    private List<Object3D> mObjects;

    private Camera mCamera;
    private Renderer mRenderer;

    public Scene() {
        mObjects = new ArrayList<>();
        mCamera = new Camera();
        mRenderer = new Renderer();
    }

    public Camera getCamera(){
        return mCamera;
    }

    public void addObject(Object3D object){
        mObjects.add(object);
    }

    public void viewport(int width, int height){
        GLContext.get().viewport(0, 0, width, height);
    }

    public void clearColor(){
        GLContext.get().clearColor(0.3f, 0.2f, 0.2f, 1);
    }

    public void display() {
        mRenderer.prepare( mCamera.getViewMatrix(), mCamera.getProjectionMatrix() );
        for (Object3D mObject : mObjects) {
            mObject.draw(mRenderer);
        }
    }
}
