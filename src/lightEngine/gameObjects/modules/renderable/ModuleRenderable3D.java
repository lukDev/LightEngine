/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.renderable;

import lightEngine.graphics.renderable.materials.Material3D;

public abstract class ModuleRenderable3D extends ModuleRenderable {

    public transient Material3D material;

    public ModuleRenderable3D setMaterial(Material3D material) {
        this.material = material;
        return this;
    }

    public abstract void render();

    public abstract void addToRenderQueue();

}
