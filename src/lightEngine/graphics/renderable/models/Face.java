/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.models;

import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f vertexIndices = new Vector3f();
    public Vector3f normalIndices = new Vector3f();
    public Vector3f uvIndices = new Vector3f();

    /**
     * A new face in 3D space.
     *
     * @param vertexIndices The indices of the vertices in a model that make up the face.
     * @param normalIndices The corresponding normal indices.
     * @param uvIndices     The corresponding texture indices.
     */
    public Face(Vector3f vertexIndices, Vector3f normalIndices, Vector3f uvIndices) {
        this.vertexIndices = vertexIndices;
        this.normalIndices = normalIndices;
        this.uvIndices = uvIndices;
    }
}
