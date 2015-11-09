/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.renderable.light;

import lightEngine.gameObjects.modules.renderable.ModuleRenderable;
import lightEngine.graphics.Renderer;
import lightEngine.util.data.DataTypeHelper;
import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class LightSource extends ModuleRenderable {

    public Vector4f color;
    public int specularLighting = 1;
    public int shadowThrowing = 1;

    public LightSource(Vector4f color) {
        Vector3f colorIntensity = VectorHelper.divideVectorByFloat(new Vector3f(color), 255f);
        this.color = new Vector4f(colorIntensity.x, colorIntensity.y, colorIntensity.z, color.w);
    }

    public LightSource setSpecularLighting(boolean specularLighting) {
        this.specularLighting = DataTypeHelper.booleanToInteger(specularLighting);
        return this;
    }

    public LightSource setShadowThrowing(boolean shadowThrowing) {
        this.shadowThrowing = DataTypeHelper.booleanToInteger(shadowThrowing);
        return this;
    }

    public void addToRenderQueue() {
        Renderer.currentRenderQueue.addLightSource(this);
    }

}
