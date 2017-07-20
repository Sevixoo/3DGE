package com.sevixoo.android3dge_app.collision;

import com.sevixoo.android3dge_app.Camera;
import com.sevixoo.android3dge_app.Object3D;
import com.sevixoo.android3dge_app.math.Vector3f;

/**
 * Created by seweryn on 20.07.2017.
 */

public interface ICollisionDetector {

    Float testCollision( SphereCollidingBody body, Vector3f ray );

}
