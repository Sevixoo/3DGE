package com.sevixoo.android3dge_app.collision;

import com.sevixoo.android3dge_app.math.Vector3f;

/**
 * Created by seweryn on 20.07.2017.
 */

public interface ICollidingBody {

    Float test(ICollisionDetector collisionDetector, Vector3f ray);

}
