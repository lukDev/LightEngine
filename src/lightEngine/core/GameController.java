/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.core;

import lightEngine.core.events.EventController;
import lightEngine.util.input.Input;
import lightEngine.util.resources.PreferenceHelper;
import lightEngine.util.resources.ResourceHelper;
import lightEngine.util.threading.ThreadHelper;
import lightEngine.util.time.TimeHelper;
import org.lwjgl.input.Mouse;

import static lightEngine.core.events.EventController.triggerEvent;

public class GameController {

    private static boolean gamePaused;
    private static boolean loading;

    /**
     * Initializes all core classes and starts the game.
     */
    public static void runGame() {

        setLoading(true);

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("lightEngine");
        Input.initialize();
        TimeHelper.initialize();

        triggerEvent("initialized");

        ThreadHelper.startThread(GameLoop::startLoop, "mengine-GameLoop"); //Physics and processing
        ThreadHelper.startThread(RenderLoop::startLoop, "mengine-RenderLoop"); //Graphics and rendering

    }

    /**
     * Returns if the game is paused or not.
     *
     * @return True if paused, false if not
     */
    public static boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * Returns if the ObjectController is loading or not.
     *
     * @return True if loading, false if not.
     */
    public static boolean isLoading() {
        return loading;
    }

    /**
     * Sets the loading variable and triggers the "loadingStarted" and "loadingStopped" events.
     *
     * @param loading If the game should be loading.
     */
    public static void setLoading(boolean loading) {
        if (!GameController.loading && loading) EventController.triggerEvent("loadingStarted");
        else if (GameController.loading && !loading) EventController.triggerEvent("loadingStopped");

        GameController.loading = loading;
    }

    /**
     * Pauses all game objects (no updates, still renders them).
     */
    public static void pauseGame() {
        triggerEvent("gamePaused");
        gamePaused = true;
    }

    /**
     * Resumes tha game and the update loop for game objects.
     */
    public static void resumeGame() {
        Mouse.setGrabbed(true);
        gamePaused = false;
        triggerEvent("gameResumed");
    }

    /**
     * Clears all bindings and stops the game.
     */
    public static void stopGame() {
        triggerEvent("gameStopped");
        ThreadHelper.stopAllThreads();
        System.exit(0);

    }

}
