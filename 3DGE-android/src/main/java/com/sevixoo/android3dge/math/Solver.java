package com.sevixoo.android3dge.math;

/**
 * Created by seweryn on 20.07.2017.
 */

public class Solver {


    public static int solveQuadratic( float a, float b, float c, float[] out ){
        float x0, x1;
        float discr = b * b - 4 * a * c;
        if (discr < 0) return -1;
        else if (discr == 0){
            x0 = x1 = - 0.5f * b / a;
            out[0] = x0;
            out[1] = x1;
            return 0;
        } else {
            float q = (b > 0) ?
                    -0.5f * (b + (float) Math.sqrt(discr)) :
                    -0.5f * (b - (float) Math.sqrt(discr));
            x0 = q / a;
            x1 = c / q;
        }
        if (x0 > x1){
            out[0] = x1;
            out[1] = x0;
        }else{
            out[0] = x0;
            out[1] = x1;
        }
        return 1;
    }


}
