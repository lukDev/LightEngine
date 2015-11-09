/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.rendering;

import lightEngine.graphics.renderable.materials.Material3D;
import lightEngine.graphics.renderable.models.Face;
import lightEngine.graphics.renderable.models.Model;
import lightEngine.graphics.renderable.models.SubModel;
import lightEngine.util.math.vectors.VectorHelper;
import lightEngine.util.resources.ResourceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelHelper {

    private static Map<String, Model> loadedModels = new HashMap<>();
    private static Map<String, Material3D> loadedMaterials = new HashMap<>();

    /**
     * Loads a model into loadedModels.
     *
     * @param fileName The name of the desired model.
     */
    private static void loadModel(String fileName) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_MODEL)));
            String line;
            int currentVertexCount = 0;
            int currentNormalCount = 0;
            int currentUvCount = 0;
            Model currentModel = new Model();
            SubModel currentSubModel = new SubModel();
            String currentMaterialName = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("mtllib "))
                    loadMaterialLibrary(line.split(" ")[1].replace(".mtl", "")); //line.split("")[1] is the stuff after mtllib
                else if (line.startsWith("usemtl ")) {
                    currentMaterialName = line.split(" ")[1];
                    currentSubModel.material = loadedMaterials.get(currentMaterialName);
                } else if (line.startsWith("o ")) {
                    currentVertexCount += currentSubModel.vertices.size();
                    currentNormalCount += currentSubModel.normals.size();
                    currentUvCount += currentSubModel.uvs.size();

                    currentSubModel = new SubModel(loadedMaterials.get(currentMaterialName));
                    currentModel.subModels.add(currentSubModel);
                } else if (line.startsWith("v ")) {

                    currentSubModel.vertices.add(new Vector3f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2]),
                      Float.valueOf(line.split(" ")[3])));

                } else if (line.startsWith("vn ")) {

                    currentSubModel.normals.add(new Vector3f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2]),
                      Float.valueOf(line.split(" ")[3])));

                } else if (line.startsWith("vt ")) {

                    currentSubModel.uvs.add(new Vector2f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2])));

                } else if (line.startsWith("f ")) {

                    //[0]: "f", [1]:([0]:vertexIndex, [1]:uvIndex, [2]: normalIndex), [...]
                    Vector3f vertexIndices = new Vector3f(
                      Float.valueOf(line.split(" ")[1].split("/")[0]) - currentVertexCount,
                      Float.valueOf(line.split(" ")[2].split("/")[0]) - currentVertexCount,
                      Float.valueOf(line.split(" ")[3].split("/")[0]) - currentVertexCount);

                    Vector3f normalIndices = new Vector3f(
                      Float.valueOf(line.split(" ")[1].split("/")[2]) - currentNormalCount,
                      Float.valueOf(line.split(" ")[2].split("/")[2]) - currentNormalCount,
                      Float.valueOf(line.split(" ")[3].split("/")[2]) - currentNormalCount);

                    Vector3f uvIndices;
                    if (!line.split(" ")[1].split("/")[1].equals(""))
                        uvIndices = new Vector3f(
                          Float.valueOf(line.split(" ")[1].split("/")[1]) - currentUvCount,
                          Float.valueOf(line.split(" ")[2].split("/")[1]) - currentUvCount,
                          Float.valueOf(line.split(" ")[3].split("/")[1]) - currentUvCount);
                    else if (currentSubModel.uvs.size() == 0) {
                        currentSubModel.uvs.add(new Vector2f());
                        uvIndices = new Vector3f(1, 1, 1);
                    } else uvIndices = new Vector3f(1, 1, 1);

                    currentSubModel.faces.add(new Face(VectorHelper.subtractVectorAndFloat(vertexIndices, 1),
                      VectorHelper.subtractVectorAndFloat(normalIndices, 1),
                      VectorHelper.subtractVectorAndFloat(uvIndices, 1)));

                }

            }

            loadedModels.put(fileName, currentModel);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Loads all materials in a library into loadedMaterials.
     *
     * @param fileName The name of the desired library.
     */
    private static void loadMaterialLibrary(String fileName) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_MATERIAL)));
            String line;
            Material3D currentMaterial = null;
            String currentMaterialName = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("newmtl ")) {
                    if (currentMaterial != null) loadedMaterials.put(currentMaterialName, currentMaterial);
                    currentMaterial = new Material3D();
                    currentMaterialName = line.split(" ")[1];
                } else if (currentMaterial != null) {

                    if (line.startsWith("Ns "))
                        currentMaterial.specularHighlightStrength = Float.valueOf(line.split(" ")[1]);
                    else if (line.startsWith("Ka ")) currentMaterial.ambientReflectivity = new Vector3f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2]),
                      Float.valueOf(line.split(" ")[3]));
                    else if (line.startsWith("Kd ")) currentMaterial.diffuseReflectivity = new Vector3f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2]),
                      Float.valueOf(line.split(" ")[3]));
                    else if (line.startsWith("Ks ")) currentMaterial.specularReflectivity = new Vector3f(
                      Float.valueOf(line.split(" ")[1]),
                      Float.valueOf(line.split(" ")[2]),
                      Float.valueOf(line.split(" ")[3]));
                    else if (line.startsWith("d ")) currentMaterial.color.a = Float.valueOf(line.split(" ")[1]);
                    else if (line.startsWith("map_Kd ")) currentMaterial.textureName = line.split(" ")[1];
                    else if (line.startsWith("illum ")) currentMaterial.type = Integer.valueOf(line.split(" ")[1]);

                }

            }

            if (currentMaterial != null) loadedMaterials.put(currentMaterialName, currentMaterial);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Returns the requested model.
     * The model and its materials get loaded if needed.
     *
     * @param name The name of the desired model.
     * @return The requested model.
     */
    public static Model getModel(String name) {

        if (loadedModels.containsKey(name)) return new Model(loadedModels.get(name).subModels);
        else {

            loadModel(name);
            return new Model(getModel(name).subModels);

        }

    }

}
