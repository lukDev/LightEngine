/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.controls;

import lightEngine.gameObjects.modules.Module;

public abstract class Controller extends Module {

    public float[] forceStrengths;
    public boolean capableOfFlying;
    public boolean sneakModeToggle;
    public boolean sprintModeToggle;
    public boolean continuouslyJumping;
    public float rotationSpeed;

    public Controller(float[] forceStrengths, boolean capableOfFlying) {

        this.forceStrengths = forceStrengths;
        this.capableOfFlying = capableOfFlying;

    }

    public void onRemoteUpdate() {
        updateObject();
    }

    protected abstract void updateObject();

}
