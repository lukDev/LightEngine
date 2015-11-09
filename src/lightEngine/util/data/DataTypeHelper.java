/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.data;


import org.lwjgl.util.vector.Vector3f;

public class DataTypeHelper {

    /**
     * Converts a boolean into an integer.
     * This is necessary for some OpenGL interactions
     *
     * @param b The boolean to be converted
     * @return The integer output
     */
    public static int booleanToInteger(boolean b) {
        if (b) return 1;
        else return 0;
    }

    public static Vector3f stringToVector3f(String s) {
        Vector3f out = new Vector3f();

        out.x = Float.valueOf(s.split(",")[0]);
        out.y = Float.valueOf(s.split(",")[1]);
        out.z = Float.valueOf(s.split(",")[2]);

        return out;
    }

}
