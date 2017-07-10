package com.sevixoo.android3dge_app;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by pi19124 on 28.06.2017.
 */

public interface FileParser {

    Object3D parse(BufferedReader reader)throws IOException;

}
