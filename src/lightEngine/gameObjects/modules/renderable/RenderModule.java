/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.renderable;

import lightEngine.gameObjects.BoundingBox;
import lightEngine.gameObjects.GameObject;
import lightEngine.graphics.Renderer;
import lightEngine.graphics.renderable.models.Face;
import lightEngine.graphics.renderable.models.Model;
import lightEngine.graphics.renderable.models.SubModel;
import lightEngine.util.rendering.ModelHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class RenderModule extends ModuleRenderable3D {

    public transient Model model;
    public String modelFileName;
    transient boolean isStatic, displayListsGenerated;
    transient int displayListIndex;
    public Vector4f color;

    public RenderModule(Model model) {
        this(model, true);
    }

    public RenderModule(Model model, boolean isStatic) {
        this.model = model;
        this.isStatic = isStatic;
    }

    public RenderModule(String modelFileName) {
        this(modelFileName, true);
    }

    public RenderModule(String modelFileName, boolean isStatic) {
        this.modelFileName = modelFileName;
        this.isStatic = isStatic;
    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        if (model == null) model = ModelHelper.getModel(modelFileName);

        Vector3f vert = model.getSize();
        vert.x /= -2; // \
        vert.y /= -2; //  > Vector gets divided by 2 and multiplied by -1
        vert.z /= -2; // /

        BoundingBox boundingBox = new BoundingBox(vert, new Vector3f(-vert.x, -vert.y, -vert.z));
        parent.addBoundingBox(boundingBox);

        model.setColor(color);

    }

    public void render() {

        //If model has a texture name, set the texture
        model.subModels.stream()
          .filter(subModel -> subModel.material.hasTexture() && subModel.material.getTexture() == null)
          .forEach(subModel -> subModel.material.setTextureFromName());

        if (isStatic && !displayListsGenerated) {

            List<Vector3f> renderVertices = new ArrayList<>();
            List<Vector3f> renderNormals = new ArrayList<>();
            List<Vector2f> renderUVs = new ArrayList<>();

            displayListIndex = Renderer.displayListCounter;

            for (SubModel subModel : model.subModels) {

                for (Face face : subModel.faces) {

                    Vector3f v1 = subModel.vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f v2 = subModel.vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f v3 = subModel.vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector2f uv1 = subModel.uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector2f uv2 = subModel.uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector2f uv3 = subModel.uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                    Vector3f n1 = subModel.normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);

                    Vector3f n2 = subModel.normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);

                    Vector3f n3 = subModel.normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, subModel.material, Renderer.RENDER_TRIANGLES);

            }

            displayListsGenerated = true;

        }

        if (isStatic && displayListsGenerated) {

            for (int i = 0; i < model.subModels.size(); i++) {

                Renderer.renderObject3D(displayListIndex + i, parent.position, parent.rotation, model.subModels.get(i).material, model.subModels.get(i).color, 0);

            }

        } else {

            List<Vector3f> renderVertices;
            List<Vector3f> renderNormals;
            List<Vector2f> renderUVs;

            for (SubModel subModel : model.subModels) {

                renderVertices = new ArrayList<>();
                renderNormals = new ArrayList<>();
                renderUVs = new ArrayList<>();

                for (Face face : subModel.faces) {

                    Vector3f v1 = subModel.vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f v2 = subModel.vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f v3 = subModel.vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector2f uv1 = subModel.uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector2f uv2 = subModel.uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector2f uv3 = subModel.uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                    Vector3f n1 = subModel.normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);

                    Vector3f n2 = subModel.normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);

                    Vector3f n3 = subModel.normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                Renderer.renderObject3D(renderVertices, renderNormals, renderUVs, subModel.material, subModel.color, Renderer.RENDER_TRIANGLES, 0);

            }

        }

    }

    public RenderModule setColor(Vector4f color) {

        this.color = color;
        return this;

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();
        model = ModelHelper.getModel(modelFileName); //Create model again

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(this);

    }

}
