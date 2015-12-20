/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.models;

import lightEngine.graphics.renderable.materials.Material3D;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;

public class SubModel {

    public Material3D material;
    public ArrayList<Vector3f> vertices;
    public ArrayList<Vector3f> normals;
    public ArrayList<Vector2f> uvs;
    public ArrayList<Face> faces;
    public Vector4f color;

    public SubModel() {
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public SubModel(Material3D material) {
        this.material = material;

        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public void setColor(Vector4f color) {

        this.color = color;

    }

}
