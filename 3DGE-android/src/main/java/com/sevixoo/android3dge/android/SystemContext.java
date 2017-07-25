package com.sevixoo.android3dge.android;

import java.io.IOException;

/**
 * Created by seweryn on 24.07.2017.
 */

public abstract class SystemContext {

    public abstract String loadFile( String path ) throws IOException;

    public abstract boolean isGLES30Supported();

}
