/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.core;

import lightEngine.gameObjects.GameObject;
import lightEngine.graphics.gui.GUIScreen;
import lightEngine.graphics.gui.GUIScreenController;
import lightEngine.graphics.renderable.LoadingScreen;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<>();
    public static List<GUIScreen> guiScreens = new ArrayList<>();

    private static List<GameObject> gameObjectsToAdd = new ArrayList<>();
    private static List<GameObject> gameObjectsToRemove = new ArrayList<>();

    private static LoadingScreen loadingScreen;

    /**
     * Adds object to the list of game objects to update and render
     *
     * @param object The game object to add to the list
     */
    public static void addGameObject(GameObject object) {
        gameObjectsToAdd.add(object);
    }

    public static void removeGameObject(GameObject object) {
        gameObjectsToRemove.add(object);
    }

    /**
     * Returns the game object in the list that corresponds to the index
     *
     * @param index The index of the game object in the list, in order of registration
     * @return The requested game object
     */
    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

    public static void addGuiScreen(GUIScreen screen) {
        guiScreens.add(screen);
    }

    public static GUIScreen getGuiScreen(int index) {
        return guiScreens.get(index);
    }

    public static List<GUIScreen> getGuiScreens() {
        return guiScreens;
    }

    /**
     * If set, the loading screen will be returned, even if it's not currently active
     *
     * @return The current loading screen
     */
    public static LoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    /**
     * Sets the loading screen to be used. It has to get activated separately.
     * Activation: Set isLoading in GameController to true
     * Deactivation: Set isLoading to false
     *
     * @param screen The loading screen to register
     */
    public static void setLoadingScreen(LoadingScreen screen) {
        loadingScreen = screen;
        GUIScreenController.addGUIScreen(loadingScreen);
    }

    static void removeGameObjects() {
        if (gameObjectsToRemove.size() > 0) {
            gameObjectsToRemove
              .forEach(GameObject::destroy);

            gameObjectsToRemove.clear();
        }
    }

    static void addNewGameObjects() {
        if (gameObjectsToAdd.size() > 0) {
            gameObjectsToAdd.stream()
              .forEach(gameObjects::add);

            gameObjectsToAdd.clear();
        }
    }

}
