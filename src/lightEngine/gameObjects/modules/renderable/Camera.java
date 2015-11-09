/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.renderable;

import lightEngine.graphics.GraphicsController;
import lightEngine.graphics.Renderer;
import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;

public class Camera extends ModuleRenderable {

    public float zoom = 0;
    private Vector3f eye;

    public Camera() {
        super();
    }

    public void onUpdate() {

        if (parent != null)
            if (!(Float.isNaN(parent.position.x) || Float.isNaN(parent.position.y) || Float.isNaN(parent.position.z)))
                eye = VectorHelper.sumVectors(
                        new Vector3f[]{VectorHelper.multiplyVectorByFloat(
                                new Vector3f(parent.percentRotation.x,
                                        parent.percentRotation.y,
                                        -parent.percentRotation.z),
                                -zoom),
                                parent.position});

    }

    public Matrix4f getViewProjectionMatrix() {

        Matrix4f projectionMatrix = new Matrix4f();
        Matrix4f viewMatrix = new Matrix4f();
        Matrix4f viewProjectionMatrix = new Matrix4f();

        float aspectRatio = (float) Display.getWidth() / (float)Display.getHeight();
        float farPlane = GraphicsController.renderDistance;
        float nearPlane = 0.1f;
        float y_scale = (float)(1f / Math.tan(Math.toRadians(GraphicsController.fieldOfView / 2)));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
        projectionMatrix.m33 = 0;

        Matrix4f.rotate((float) Math.toRadians(parent.rotation.x), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(parent.rotation.y), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(parent.rotation.z), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.translate(VectorHelper.negateVector(parent.position), viewMatrix, viewMatrix);

        Matrix4f.mul(projectionMatrix, viewMatrix, viewProjectionMatrix);

        return viewProjectionMatrix;

    }

    /*public void render() {

        glLoadIdentity();

        if (zoom == 0) {

            glRotatef(parent.rotation.x, 1, 0, 0);
            glRotatef(parent.rotation.y, 0, 1, 0);
            glRotatef(parent.rotation.z, 0, 0, 1);

            glTranslatef(-parent.position.x, -parent.position.y, -parent.position.z);

        } else {

            Vector3f up = new Vector3f(0, 1, 0);

            if (parent.rotation.z != 0) {

                float radiantRotation = (float) -Math.toRadians(parent.rotation.z);

                Matrix3f zAxisRotationMatrix = new Matrix3f(
                  new Vector3f((float) Math.cos(radiantRotation), (float) -Math.sin(radiantRotation), 0),
                  new Vector3f((float) Math.sin(radiantRotation), (float) Math.cos(radiantRotation), 0),
                  new Vector3f(0, 0, 1)
                );

                up = zAxisRotationMatrix.multiplyByVector(up);

            }

            gluLookAt(eye.x, eye.y, eye.z, parent.position.x, parent.position.y, parent.position.z, up.x, up.y, up.z);

        }

    }*/

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.setCamera(this);

    }

}