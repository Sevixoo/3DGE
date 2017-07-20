package com.sevixoo.android3dge_app.collision;

import com.sevixoo.android3dge_app.Camera;
import com.sevixoo.android3dge_app.Object3D;
import com.sevixoo.android3dge_app.math.Solver;
import com.sevixoo.android3dge_app.math.Vector3f;

/**
 * Created by seweryn on 20.07.2017.
 */
public class WorldCollisionDetector implements ICollisionDetector {

    private Camera mCamera;

    public WorldCollisionDetector(Camera mCamera) {
        this.mCamera = mCamera;
    }

    @Override
    public Float testCollision(SphereCollidingBody body, Vector3f ray) {
        Vector3f origin = new Vector3f(mCamera.getPosition());
        Vector3f center = body.center();
        float radius2 = body.radius()*body.radius();

        // analytic solution
        Vector3f L = origin.minus(center);
        float a = ray.dot(ray);
        float b = 2 * ray.dot(L);
        float c = L.dot(L) - radius2;
        float[] t = new float[2];
        if (Solver.solveQuadratic(a, b, c, t) == -1) return null;

        if (t[0] < 0) {
            if (t[1] < 0) return null;
            return t[1];
        }
        return t[0];
    }

}
