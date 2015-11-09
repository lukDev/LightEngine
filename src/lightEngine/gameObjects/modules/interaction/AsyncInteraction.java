/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.interaction;

public abstract class AsyncInteraction extends Interaction implements Runnable {

    public void run() {
        interact();
    }

    public abstract void interact();

}
