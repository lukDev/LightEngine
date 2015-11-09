/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.core;

import lightEngine.gameObjects.modules.gui.GUIElement;
import lightEngine.graphics.GraphicsController;
import lightEngine.graphics.RenderQueue;
import lightEngine.graphics.Renderer;
import lightEngine.util.rendering.ShaderHelper;
import lightEngine.util.time.TimeHelper;
import org.lwjgl.opengl.Display;

public class RenderLoop {

    /**
     * Creates a new OpenGL window, sets the standard shader and runs the render loop
     */
    public static void startLoop() {

        GraphicsController.createDisplay();
        ShaderHelper.addShader("lighting");
        ShaderHelper.addShader("shadowMap");

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            GraphicsController.clearScreen();
            Renderer.currentRenderQueue = new RenderQueue();

            if (!GameController.isLoading()) {
                    //Renders all the gameObjects
                    ObjectController.gameObjects.forEach(lightEngine.gameObjects.GameObject::addToRenderQueue);
                    ObjectController.guiScreens.forEach(screen ->
                            screen.getElements().forEach(GUIElement::addToRenderQueue));
                Renderer.currentRenderQueue.render();
            }

            ObjectController.getLoadingScreen().render();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
