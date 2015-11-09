/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.rendering;

import lightEngine.core.ObjectController;
import lightEngine.gameObjects.GameObject;
import lightEngine.gameObjects.modules.renderable.Camera;
import lightEngine.graphics.Renderer;
import lightEngine.util.math.vectors.Matrix3f;
import lightEngine.util.math.vectors.VectorHelper;
import lightEngine.util.resources.PreferenceHelper;
import org.lwjgl.util.vector.Vector3f;

public class RenderHelper {

    /**
     * Used for frustum culling.
     *
     * @param vector The vertex to check.
     * @return True if on screen, false if not.
     */
    public static boolean isVertexOnScreen(Vector3f vector) {

        boolean theoreticallyOnScreen = false;

        Camera camera = null;

        if (Renderer.currentRenderQueue.camera != null) camera = Renderer.currentRenderQueue.camera;

        else {

            for (GameObject obj : ObjectController.gameObjects) {

                Camera objectCamera = (Camera) obj.getModule(Camera.class);

                if (objectCamera != null) {

                    camera = objectCamera;
                    break;

                }

            }

        }

        if (camera != null) {

            vector = VectorHelper.subtractVectors(vector, camera.parent.position);
            vector = VectorHelper.normalizeVector(vector);

            float fov = (float) Math.toRadians(PreferenceHelper.getFloat("fieldOfView"));

            Vector3f xAxis = new Vector3f(1, 0, 0);
            Vector3f yAxis = new Vector3f(0, 1, 0);
            Vector3f zAxis = new Vector3f(0, 0, 1);

            Matrix3f yAxisRotationMatrix = new Matrix3f(new Vector3f((float) Math.cos(-fov / 2), 0, (float) Math.sin(-fov / 2)),
              new Vector3f(0, 1, 0),
              new Vector3f(-(float) Math.sin(-fov / 2), 0, (float) Math.cos(-fov / 2)));

            Vector3f leftLowerCorner = yAxisRotationMatrix.multiplyByVector(zAxis);

            yAxisRotationMatrix = new Matrix3f(new Vector3f((float) Math.cos(fov / 2), 0, (float) Math.sin(fov / 2)),
              new Vector3f(0, 1, 0),
              new Vector3f(-(float) Math.sin(fov / 2), 0, (float) Math.cos(fov / 2)));

            Vector3f rightUpperCorner = yAxisRotationMatrix.multiplyByVector(zAxis);

            Matrix3f xAxisRotationMatrix = new Matrix3f(new Vector3f(1, 0, 0),
              new Vector3f(0, (float) Math.cos(fov / 2), -(float) Math.sin(fov / 2)),
              new Vector3f(0, (float) Math.sin(fov / 2), (float) Math.cos(fov / 2)));

            leftLowerCorner = xAxisRotationMatrix.multiplyByVector(leftLowerCorner);

            xAxisRotationMatrix = new Matrix3f(new Vector3f(1, 0, 0),
              new Vector3f(0, (float) Math.cos(-fov / 2), -(float) Math.sin(-fov / 2)),
              new Vector3f(0, (float) Math.sin(-fov / 2), (float) Math.cos(-fov / 2)));

            rightUpperCorner = xAxisRotationMatrix.multiplyByVector(rightUpperCorner);

            Vector3f alphaIndicator = new Vector3f(camera.parent.percentRotation.x, 0, camera.parent.percentRotation.z);
            float alpha = VectorHelper.getAngle(zAxis, alphaIndicator);

            if (VectorHelper.getScalarProduct(camera.parent.percentRotation, xAxis) < 0) alpha = -alpha;

            yAxisRotationMatrix = new Matrix3f(new Vector3f((float) Math.cos(alpha), 0, (float) Math.sin(alpha)),
              new Vector3f(0, 1, 0),
              new Vector3f(-(float) Math.sin(alpha), 0, (float) Math.cos(alpha)));

            vector = yAxisRotationMatrix.multiplyByVector(vector);

            if (camera.parent.percentRotation.y != 0) {

                Vector3f betaIndicator = new Vector3f(0, camera.parent.percentRotation.y, 0);
                float beta = VectorHelper.getAngle(zAxis, betaIndicator);

                if (VectorHelper.getScalarProduct(camera.parent.percentRotation, yAxis) < 0) beta = -beta;

                xAxisRotationMatrix = new Matrix3f(new Vector3f(1, 0, 0),
                  new Vector3f(0, (float) Math.cos(beta), -(float) Math.sin(beta)),
                  new Vector3f(0, (float) Math.sin(beta), (float) Math.cos(beta)));

                vector = xAxisRotationMatrix.multiplyByVector(vector);

            }

            theoreticallyOnScreen = vector.x >= leftLowerCorner.x && vector.x <= rightUpperCorner.x && vector.y >= leftLowerCorner.y && vector.y <= rightUpperCorner.y;

        }

        return theoreticallyOnScreen;

    }

}
