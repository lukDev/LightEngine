/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.interaction;

import lightEngine.gameObjects.GameObject;

abstract class Interaction {

    protected GameObject parent;
    protected InteractionModule caller;

    public void setParent(GameObject object) {

        parent = object;

        caller = (InteractionModule) parent.getModule(InteractionModule.class);

    }

}
