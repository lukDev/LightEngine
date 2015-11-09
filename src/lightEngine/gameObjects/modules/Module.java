/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules;

import lightEngine.gameObjects.GameObject;

import java.io.Serializable;

public abstract class Module implements Serializable {

    public GameObject parent;

    /**
     * This is called by the game objects this module is part of.
     * Use this method to interact with your dependencies and to initialize your module
     *
     * @param obj The parent of this module
     */
    public void onCreation(GameObject obj) {
        parent = obj;
    }

    /**
     * This method updates your module.
     * Do not use it for rendering but for everything else.
     * This is called every tick
     */
    public void onUpdate() {
    }

    /**
     * Clear your module data here.
     * This is called when your module gets removed from the game object via removeModule()
     */
    public void onDestroy() {
    }

    /**
     * Used for serialization.
     * Set all objects that don't have to be saved (e.g. textures) in your module to null.
     * You can create variables like Strings that save information about these objects (e.g. the texture name)
     */
    public void onSave() {
    }

    /**
     * Used for de-serialization.
     * Load all objects that you set to null in onSave() back into your module
     */
    public void onLoad() {
    }

}
