/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.input;

import lightEngine.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static Map<String, Object[]> inputEventAssignments;
    private static boolean[] keyStates;
    private static boolean[] buttonStates;

    /**
     * Initializes the state arrays and the map of key assignments.
     */
    public static void initialize() {
        inputEventAssignments = new HashMap<>();
        keyStates = new boolean[Keyboard.getKeyCount()];
        buttonStates = new boolean[(int) MathHelper.clampMin(Mouse.getButtonCount(), 3)];
    }

    /**
     * @param name The name of the event.
     * @return true, if the event is triggered, false if not.
     */
    public static boolean inputEventTriggered(String name) {

        boolean eventTriggered = false;

        Object[] event = inputEventAssignments.get(name);
        InputEventType type = (InputEventType) event[1];
        int code = (int) event[2];

        if ((boolean) event[0]) {

            switch (type) {
                case PRESSED:
                    if (isKeyPressed(code))
                        eventTriggered = true;
                    break;

                case ACTIVATED:
                    if (isKeyDown(code))
                        eventTriggered = true;
                    break;
                case RELEASED:
                    if (isKeyUp(code))
                        eventTriggered = true;
                    break;
            }

        } else {
            switch (type) {
                case PRESSED:
                    if (isButtonPressed(code))
                        eventTriggered = true;
                    break;

                case ACTIVATED:
                    if (isButtonDown(code))
                        eventTriggered = true;
                    break;

                case RELEASED:
                    if (isButtonUp(code))
                        eventTriggered = true;
                    break;
            }
        }

        return eventTriggered;

    }

    /**
     * Tells you if a certain key is currently in the "pressed" or "down" state.
     *
     * @param keyCode The desired key.
     * @return True if the key is pressed, false if not.
     */
    public static boolean isKeyPressed(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    /**
     * Tells you if a certain mouse button is currently in the "pressed" or "down" state.
     *
     * @param button The desired mouse button.
     * @return True if the button is pressed, false if not.
     */
    public static boolean isButtonPressed(int button) {
        return Mouse.isButtonDown(button);
    }

    /**
     * Tells you if a certain key was just pressed down.
     *
     * @param keyCode The desired key.
     * @return True if the key was just pressed down, false if not.
     */
    public static boolean isKeyDown(int keyCode) {
        boolean isAlreadyActivated = keyStates[keyCode];
        keyStates[keyCode] = isKeyPressed(keyCode);
        return keyStates[keyCode] != isAlreadyActivated && !isAlreadyActivated;
    }

    /**
     * Tells you if a certain mouse button was just pressed down.
     *
     * @param button The desired mouse button.
     * @return True if the button was just pressed down, false if not.
     */
    public static boolean isButtonDown(int button) {
        boolean isAlreadyActivated = buttonStates[button];
        buttonStates[button] = isButtonPressed(button);
        return buttonStates[button] != isAlreadyActivated && !isAlreadyActivated;
    }

    /**
     * Tells you if a certain key was just released.
     *
     * @param keyCode The desired key.
     * @return True if the key was just released, false if not.
     */
    public static boolean isKeyUp(int keyCode) {
        boolean isAlreadyActivated = keyStates[keyCode];
        keyStates[keyCode] = isKeyPressed(keyCode);
        return keyStates[keyCode] != isAlreadyActivated && isAlreadyActivated;
    }

    /**
     * Tells you if a certain mouse button was just released.
     *
     * @param button The desired mouse button.
     * @return True if the button was just released, false if not.
     */
    public static boolean isButtonUp(int button) {
        boolean isAlreadyActivated = buttonStates[button];
        buttonStates[button] = isButtonPressed(button);
        return buttonStates[button] != isAlreadyActivated && isAlreadyActivated;
    }

    /**
     * Assign a name to a specific key (e.g. "forward" to Keyboard.KEY_W).
     *
     * @param name The name of the key, e.g. "forward".
     * @param code The keycode, usually Keyboard.KEY_x where x is the desired key.
     */
    public static void assignInputEvent(String name, boolean keyBoard, InputEventType type, int code) {
        inputEventAssignments.put(name, new Object[]{keyBoard, type, code});

        if (keyBoard) keyStates[code] = false;
        else buttonStates[code] = false;
    }

    /**
     * Releases the desired key from its binding.
     *
     * @param name The name of the key, e.g. "forward".
     */
    public static void unAssignInputEvent(String name) {

        inputEventAssignments.remove(name);

    }

}

