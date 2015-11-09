/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.math.vectors;

import org.lwjgl.util.vector.Vector3f;

public class Matrix3f extends Matrix {

    private Vector3f firstLine;
    private Vector3f secondLine;
    private Vector3f thirdLine;

    public Matrix3f(Vector3f firstLine, Vector3f secondLine, Vector3f thirdLine) {

        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;

    }

    public Vector3f multiplyByVector(Vector3f vector) {

        Vector3f result = new Vector3f();

        result.x = vector.x * firstLine.x + vector.y * firstLine.y + vector.z * firstLine.z;
        result.y = vector.x * secondLine.x + vector.y * secondLine.y + vector.z * secondLine.z;
        result.z = vector.x * thirdLine.x + vector.y * thirdLine.y + vector.z * thirdLine.z;

        return result;

    }

}
