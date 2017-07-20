package com.sevixoo.android3dge_app.collision;

import com.sevixoo.android3dge_app.math.Vector3f;

/**
 * Created by seweryn on 20.07.2017.
 */

public class SphereCollidingBody implements ICollidingBody {

    private float mRadius;

    private ValueProvider<Vector3f> mPositionProvider;

    public SphereCollidingBody( ValueProvider<Vector3f> positionProvider, float radius) {
        this.mRadius = radius;
        this.mPositionProvider = positionProvider;
    }

    public float radius(){
        return mRadius;
    }

    public Vector3f center(){
        return mPositionProvider.get();
    }

    @Override
    public Float test(ICollisionDetector collisionDetector, Vector3f ray) {
        return collisionDetector.testCollision(this, ray);
    }
}
