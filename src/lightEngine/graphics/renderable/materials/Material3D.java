/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.materials;

import org.lwjgl.util.vector.Vector3f;

public class Material3D extends Material {

    public float specularHighlightStrength = 96.078431f;
    public Vector3f ambientReflectivity = new Vector3f();
    public Vector3f diffuseReflectivity = new Vector3f(0.64f, 0.64f, 0.64f);
    public Vector3f specularReflectivity = new Vector3f(0.5f, 0.5f, 0.5f);
    public Vector3f emit;
    public int type = 2;

    public Material3D() {
        super();
    }

}
