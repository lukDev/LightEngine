/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.core;

import lightEngine.gameObjects.GameObject;
import lightEngine.util.time.TimeHelper;
import org.lwjgl.opengl.Display;

public class GameLoop {

    /**
     * Runs the update loop
     */
    public static void startLoop() {

        //Waiting for Display creation
        while (!Display.isCreated()) {
            TimeHelper.sleep(10);
        }

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            TimeHelper.updateDeltaTime();

            if (!GameController.isLoading()) {

                //Adds all new game objects that were added via ObjectController.addGameObject() to the game object list
                ObjectController.addNewGameObjects();
                //Removes all game objects that should be removed
                ObjectController.removeGameObjects();

                ObjectController.gameObjects.forEach(GameObject::update);

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
