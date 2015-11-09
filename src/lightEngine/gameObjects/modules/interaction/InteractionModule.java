/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.interaction;

import lightEngine.core.ObjectController;
import lightEngine.gameObjects.GameObject;
import lightEngine.gameObjects.modules.Module;
import lightEngine.gameObjects.modules.controls.Controller;
import lightEngine.gameObjects.modules.gui.GUIElement;
import lightEngine.gameObjects.modules.gui.modules.GUIText;
import lightEngine.util.input.Input;
import lightEngine.util.input.InputEventType;
import lightEngine.util.math.vectors.VectorHelper;
import lightEngine.util.threading.ThreadHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class InteractionModule extends Module {

    public boolean enabled;
    public int interactionKey;
    public String interactionKeyDescription;
    public String interactionDescription;
    public String interactionInstruction;
    public Interaction interaction;
    public GUIText interactionInstructionText;
    private float radius;
    private float[] controllerDistances;
    private float maxControllerLookAngle;
    private float[] controllerLookAngles;
    private List<GameObject> controlledGameObjects;

    public InteractionModule(boolean enabled, float radius, Interaction interaction) {

        this(enabled, radius, -1, "", 180, interaction);

    }

    public InteractionModule(boolean enabled, float radius, int interactionKey, Interaction interaction) {

        this(enabled, radius, interactionKey, "interact", 180, interaction);

    }

    public InteractionModule(boolean enabled, float radius, int interactionKey, String interactionDescription, Interaction interaction) {

        this(enabled, radius, interactionKey, interactionDescription, 180, interaction);

    }

    public InteractionModule(boolean enabled, float radius, int interactionKey, String interactionDescription, float maxControllerLookAngle, Interaction interaction) {

        this.enabled = enabled;
        this.interaction = interaction;
        this.radius = radius;
        this.interactionDescription = interactionDescription;
        this.maxControllerLookAngle = (float) Math.toRadians(maxControllerLookAngle);
        this.interactionKey = interactionKey;

        if (interactionKey != -1) {

            interactionKeyDescription = Keyboard.getKeyName(interactionKey);
            Input.assignInputEvent(interactionKeyDescription, true, InputEventType.ACTIVATED, interactionKey);

        }

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        interaction.setParent(parent);

        if (interactionKeyDescription != null) {

            String interactionInstruction = interactionKeyDescription.toUpperCase() + ": " + interactionDescription;

            interactionInstructionText = new GUIText(interactionInstruction, 15);

            parent.addModule(
              new GUIElement(new Vector2f(Display.getWidth() / 2, Display.getHeight() - 100), new Vector2f()).addModule(interactionInstructionText)
            );

            this.interactionInstruction = interactionInstruction;

        }

    }

    public boolean isInteracted() {

        if (enabled) {

            boolean interacted = false;

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (interactionKeyDescription == null) {

                    if (controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                        interacted = true;
                        break;

                    }

                } else {

                    if (Input.inputEventTriggered(interactionKeyDescription) && controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                        interacted = true;
                        break;

                    }

                }

            }

            return interacted;

        } else return false;

    }

    private void setControllerData() {

        for (int count = 0; count < controlledGameObjects.size(); count++) {

            GameObject controlledObject = controlledGameObjects.get(count);

            Vector3f distanceVector = VectorHelper.subtractVectors(parent.position, controlledObject.position);

            controllerDistances[count] = VectorHelper.getAbs(distanceVector);

            Vector3f percentRotation = new Vector3f(controlledObject.percentRotation.x, controlledObject.percentRotation.y, -controlledObject.percentRotation.z);
            controllerLookAngles[count] = VectorHelper.getAngle(distanceVector, percentRotation);

        }

    }

    private void setControlledGameObjects() {

        controlledGameObjects = new ArrayList<GameObject>();

        for (GameObject object : ObjectController.gameObjects) {

            if (object.getModule(Controller.class) != null) controlledGameObjects.add(object);

        }

        controllerDistances = new float[controlledGameObjects.size()];
        controllerLookAngles = new float[controlledGameObjects.size()];

    }

    public void onUpdate() {

        if (controlledGameObjects == null) setControlledGameObjects();

        setControllerData();

        if (interactionInstruction != null) {

            boolean enabled = false;

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                    enabled = true;
                    break;

                }

            }

            if (enabled && this.enabled)
                interactionInstructionText.text = interactionInstruction;

            else
                interactionInstructionText.text = "";

        }

        if (isInteracted()) {

            if (interaction instanceof StandardInteraction) ((StandardInteraction) interaction).interact();

            else ThreadHelper.startThread(((AsyncInteraction) interaction));

        }

    }

}
