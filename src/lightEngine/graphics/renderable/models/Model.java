/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.models;

import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;

public class Model {

    public ArrayList<SubModel> subModels;

    public Model() {
        subModels = new ArrayList<>();
    }

    public Model(ArrayList<SubModel> subModels) {
        this.subModels = subModels;

        //Centering the model
        Vector3f center = getCenter();
        for (SubModel subModel : subModels)
            for (int i = 0; i < subModel.vertices.size(); i++)
                subModel.vertices.set(i, VectorHelper.subtractVectors(subModel.vertices.get(i), center));
    }

    public ArrayList<Vector3f> getVertices() {

        ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
        for (SubModel subModel : subModels) {

            vertices.addAll(subModel.vertices);

        }

        return vertices;

    }

    public ArrayList<Vector3f> getNormals() {

        ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
        for (SubModel subModel : subModels) {

            normals.addAll(subModel.normals);

        }

        return normals;

    }

    public ArrayList<Vector2f> getUvs() {

        ArrayList<Vector2f> uvs = new ArrayList<Vector2f>();
        for (SubModel subModel : subModels) {

            uvs.addAll(subModel.uvs);

        }

        return uvs;

    }

    public ArrayList<Face> getFaces() {

        ArrayList<Face> faces = new ArrayList<Face>();
        for (SubModel subModel : subModels) {

            faces.addAll(subModel.faces);

        }

        return faces;

    }

    public Vector3f getSize() {
        Vector3f[] extremeVertexPositions = getExtremeVertexPositions();
        return VectorHelper.subtractVectors(extremeVertexPositions[1], extremeVertexPositions[0]);
    }

    private Vector3f[] getExtremeVertexPositions() {

        Vector3f maxVertexPos = new Vector3f();
        Vector3f minVertexPos = new Vector3f();

        for (SubModel subModel : subModels)
            for (Vector3f vertex : subModel.vertices) {

                if (vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
                else if (vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

                if (vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
                else if (vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

                if (vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
                else if (vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

            }

        return new Vector3f[]{minVertexPos, maxVertexPos};

    }

    public Vector3f getCenter() {

        Vector3f[] extremeVertexPositions = getExtremeVertexPositions();

        return VectorHelper.sumVectors(new Vector3f[]
          {VectorHelper.divideVectorByFloat(VectorHelper.subtractVectors(extremeVertexPositions[1], extremeVertexPositions[0]), 2), extremeVertexPositions[0]});

    }

    public void setColor(Vector4f color) {

        for (SubModel subModel : subModels)
            subModel.setColor(color);

    }

    public float getMass() {

        return 60;

    }

}
