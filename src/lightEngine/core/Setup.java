package lightEngine.core;

import lightEngine.gameObjects.modules.gui.GUIElement;
import lightEngine.graphics.GraphicsController;
import lightEngine.graphics.gui.GUIScreen;
import lightEngine.graphics.gui.GUIScreenController;
import lightEngine.graphics.renderable.LoadingScreen;
import lightEngine.util.input.Input;
import lightEngine.util.input.InputEventType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static lightEngine.core.events.EventController.addEventHandler;

public class Setup {

    static GUIScreen menuScreen;
    static GUIScreen inGame;
    static GUIScreen alwaysActive;

    static LoadingScreen standardLoadingScreen;

    public static void setupDefaults() {

        standardLoadingScreen = new LoadingScreen("loadingScreen");

        ObjectController.setLoadingScreen(standardLoadingScreen);
        Mouse.setGrabbed(true);

        alwaysActive = new GUIScreen(true);
        inGame = new GUIScreen("gameResumed", "gamePaused", true);
        menuScreen = new GUIScreen("gamePaused", "gameResumed");

        GameController.runGame();
        addEventHandler("gamePaused", () -> Mouse.setGrabbed(false));
        addEventHandler("gameResumed", () -> Mouse.setGrabbed(true));

        GUIScreenController.addGUIScreen(menuScreen);
        GUIScreenController.addGUIScreen(inGame);
        GUIScreenController.addGUIScreen(alwaysActive);

        Input.assignInputEvent("pauseGame", true, InputEventType.ACTIVATED, Keyboard.KEY_ESCAPE);
        Input.assignInputEvent("screenshot", true, InputEventType.ACTIVATED, Keyboard.KEY_F2);

        alwaysActive.addElement(new GUIElement(new Vector2f()) {
            @Override
            public void onUpdate() {
                super.onUpdate();
                if (Input.inputEventTriggered("pauseGame")) {
                    if (GameController.isGamePaused()) GameController.resumeGame();
                    else GameController.pauseGame();
                }
            }

            @Override
            public void render() {
                super.render();
                if (Input.inputEventTriggered("screenshot")) GraphicsController.takeScreenshot();
            }
        });

    }

}
