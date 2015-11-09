/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.controls;

import lightEngine.gameObjects.modules.physics.MovementModule;
import lightEngine.graphics.GraphicsController;
import lightEngine.util.input.Input;
import lightEngine.util.input.InputEventType;
import lightEngine.util.resources.PreferenceHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ControllerManual extends Controller {

    int fovModifier = 2;

    public ControllerManual(float[] forceStrengths, boolean capableOfFlying) {

        super(forceStrengths, capableOfFlying);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

        Input.assignInputEvent("forward", true, InputEventType.PRESSED, Keyboard.KEY_W);
        Input.assignInputEvent("backward", true, InputEventType.PRESSED, Keyboard.KEY_S);
        Input.assignInputEvent("right", true, InputEventType.PRESSED, Keyboard.KEY_D);
        Input.assignInputEvent("left", true, InputEventType.PRESSED, Keyboard.KEY_A);

        Input.assignInputEvent("up", true, InputEventType.PRESSED, Keyboard.KEY_E);
        Input.assignInputEvent("down", true, InputEventType.PRESSED, Keyboard.KEY_Q);

        Input.assignInputEvent("jump", true, InputEventType.ACTIVATED, Keyboard.KEY_SPACE);

        Input.assignInputEvent("sprint", true, InputEventType.PRESSED, Keyboard.KEY_LSHIFT);

        Input.assignInputEvent("sneak", true, InputEventType.ACTIVATED, Keyboard.KEY_C);

    }


    /**
     * update method for the game object the module is bound to
     */

    public void updateObject() {

        if (true) { //TODO: check if there is a game pad plugged in

            if (Mouse.isButtonDown(1)) {

                int change = Mouse.getDWheel();

                if (change > 0) fovModifier++;

                else if (change < 0 && fovModifier > 2) fovModifier--;

                GraphicsController.fieldOfView = PreferenceHelper.getInteger("fieldOfView") / fovModifier;
                rotationSpeed = 0.02f;

            } else {

                GraphicsController.fieldOfView = PreferenceHelper.getInteger("fieldOfView");
                rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

            }

            MovementModule movementComponent = (MovementModule) parent.getModule(MovementModule.class);

            if (movementComponent != null) {

                //Calculating the rotation
                float pitch = parent.rotation.x;
                float yaw = parent.rotation.y;

                float maxUpAngle = 89.9f;
                float maxDownAngle = -89.9f;

                float deltaMouseX = Mouse.getDX() * rotationSpeed;
                float deltaMouseY = Mouse.getDY() * rotationSpeed;

                if (yaw + deltaMouseX >= 360) {

                    yaw = yaw + deltaMouseX - 360;

                } else if (yaw + deltaMouseX < 0) {

                    yaw = 360 - yaw + deltaMouseX;

                } else {

                    yaw += deltaMouseX;

                }

                if (pitch - deltaMouseY >= maxDownAngle && pitch - deltaMouseY <= maxUpAngle) {

                    pitch += -deltaMouseY;

                } else if (pitch - deltaMouseY < maxDownAngle) {

                    pitch = maxDownAngle;

                } else if (pitch - deltaMouseY > maxUpAngle) {

                    pitch = maxUpAngle;

                }

                movementComponent.rotate(pitch, yaw);

                if (Input.inputEventTriggered("sprint")) movementComponent.sprint();
                if (Input.inputEventTriggered("sneak")) movementComponent.sneak();

                if (Input.inputEventTriggered("forward")) movementComponent.moveForward();
                if (Input.inputEventTriggered("backward")) movementComponent.moveBackward();
                if (Input.inputEventTriggered("right")) movementComponent.moveLeft();
                if (Input.inputEventTriggered("left")) movementComponent.moveRight();

                if (Input.inputEventTriggered("up") && capableOfFlying) movementComponent.moveUp();
                if (Input.inputEventTriggered("down") && capableOfFlying) movementComponent.moveDown();

                if (Input.inputEventTriggered("jump")) movementComponent.jump();

            }

        } else {


        }

    }

}
