package lightEngine.core;

import lightEngine.Main;
import lightEngine.core.events.EventController;
import lightEngine.core.events.EventHandler;
import lightEngine.gameObjects.GameObject;
import lightEngine.gameObjects.modules.controls.ControllerManual;
import lightEngine.gameObjects.modules.gui.GUIElement;
import lightEngine.gameObjects.modules.gui.modules.buttons.GUIButton;
import lightEngine.gameObjects.modules.interaction.AsyncInteraction;
import lightEngine.gameObjects.modules.interaction.InteractionModule;
import lightEngine.gameObjects.modules.interaction.StandardInteraction;
import lightEngine.gameObjects.modules.physics.MovementModule;
import lightEngine.gameObjects.modules.renderable.Camera;
import lightEngine.gameObjects.modules.renderable.RenderModule;
import lightEngine.gameObjects.modules.renderable.light.DirectionalLightSource;
import lightEngine.gameObjects.modules.renderable.light.GlobalLightSource;
import lightEngine.gameObjects.modules.renderable.light.SpotLightSource;
import lightEngine.graphics.GraphicsController;
import lightEngine.graphics.Renderer;
import lightEngine.graphics.gui.GUIScreen;
import lightEngine.graphics.gui.GUIScreenController;
import lightEngine.graphics.renderable.LoadingScreen;
import lightEngine.util.input.Input;
import lightEngine.util.input.InputEventType;
import lightEngine.util.math.MathHelper;
import lightEngine.util.math.Randomizer;
import lightEngine.util.math.vectors.Matrix3f;
import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            }

            @Override
            public void render() {
                super.render();
                if (Input.inputEventTriggered("pauseGame")) {
                    if (GameController.isGamePaused()) {
                        GameController.resumeGame();
                        EventController.triggerEvent("gameResumed");
                    }
                    else {
                        GameController.pauseGame();
                        EventController.triggerEvent("gamePaused");
                    }
                }
                if (Input.inputEventTriggered("screenshot")) GraphicsController.takeScreenshot();
            }
        });

    }

    public static void loadScene(int[] normalLight, int directionalLight, int[] turnableColorLights, int[] colorLights, int[] moveableLights,
                                 int spheres1, int spheres2, int monkeys1, int monkeys2, int objects3) {

        new GameObject(new Vector3f(), new Vector3f())
                .addModule(new RenderModule("skybox").setColor(new Vector4f(0.392f, 0.584f, 0.929f, 1)))
                .createModules();

        new GameObject(new Vector3f(0, 5, 0), new Vector3f(0, 0, 0))
                .addModule(new MovementModule())
                .addModule(new RenderModule("sphere"))
                .addModule(
                        new ControllerManual(
                                new float[]{0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0}, //forward, backward, left, right, down, up, jump
                                true //Can fly
                        )
                )
                .addModule(new Camera())
                .createModules();

        new GameObject(new Vector3f(0, 0, 0), new Vector3f())
                .addModule(new RenderModule("bigPlane", true))
                .createModules();

        new GameObject(new Vector3f(0, 10, 60), new Vector3f())
                .addModule(new RenderModule("rotatedPlane"))
                .createModules();

        normalLight[0] = (int) MathHelper.clamp(normalLight[0], 0, 8);

        for (int i = 0; i < normalLight[0]; i++) {

            new GameObject(new Vector3f(15 + ((i % 2 == 0) ? 2.5f * i : -2.5f * i), 30, 10), new Vector3f(45, 180, 0))
                    .addModule(new RenderModule("sphere2"))
                    .addModule(new SpotLightSource(
                            new Vector4f(255, 255, 255, 500 / normalLight[0]), normalLight[1])
                            .setSpecularLighting(false))
                    .createModules();

        }

        directionalLight = (int) MathHelper.clamp(directionalLight, 0, 2);

        for (int i = 0; i < directionalLight; i++) {

            new GameObject(new Vector3f(35 - 35 * i, 30, 20 * i), new Vector3f(45, 180 + i * 90, 0))
                    .addModule(new RenderModule("sphere2"))
                    .addModule(new DirectionalLightSource(300 / directionalLight))
                    .createModules();

        }

        turnableColorLights[0] = (int) MathHelper.clamp(turnableColorLights[0], 0, 3);

        boolean[] types = new boolean[3];
        Vector3f[] positions = new Vector3f[] {new Vector3f(0, 20, 42), new Vector3f(-7.5f, 30, 42), new Vector3f(7.5f, 30, 42)};

        for (int i = 0; i < turnableColorLights[0]; i++) {

            int type = Randomizer.getRandomInt(0, 2);

            while (types[type]) {
                type = Randomizer.getRandomInt(0, 2);
            }

            switch (type) {

                case 0:

                    new GameObject(positions[i], new Vector3f(0, 180, 0))
                            .addModule(new RenderModule("sphere2"))
                            .addModule(new SpotLightSource(new Vector4f(255, 0, 0, 400), turnableColorLights[1])
                                    .setSpecularLighting(false))
                            .addModule(new InteractionModule(true, 20, Keyboard.KEY_Z, "zoom", 10, new AsyncInteraction() {

                                public void interact() {

                                    try {

                                        SpotLightSource spotLightSource = (SpotLightSource) parent.getModule(SpotLightSource.class);
                                        float modifier = (float) Math.toRadians(0.083);

                                        for (int i = 0; i < 250; i++) {
                                            spotLightSource.angle += modifier;
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 500; i++) {
                                            spotLightSource.angle -= modifier;
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 250; i++) {
                                            spotLightSource.angle += modifier;
                                            Thread.sleep(10);
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        System.exit(1);
                                    }

                                }

                            }))
                            .createModules();

                    break;

                case 1:

                    new GameObject(positions[i], new Vector3f(0, 180, 0))
                            .addModule(new RenderModule("sphere2"))
                            .addModule(new SpotLightSource(new Vector4f(0, 255, 0, 400), turnableColorLights[1])
                                    .setSpecularLighting(false))
                            .addModule(new InteractionModule(true, 20, Keyboard.KEY_R, "rotate", 10, new AsyncInteraction() {

                                public void interact() {

                                    try {

                                        float yRotationDeg = -0.15f;
                                        float zRotationDeg = -(360f / 700f);

                                        float yRotation = (float) Math.toRadians(yRotationDeg);
                                        float zRotation = (float) Math.toRadians(zRotationDeg);

                                        Matrix3f yAxisRotationMatrix = new Matrix3f(
                                                new Vector3f((float) Math.cos(yRotation), 0, (float) Math.sin(yRotation)),
                                                new Vector3f(0, 1, 0),
                                                new Vector3f((float) -Math.sin(yRotation), 0, (float) Math.cos(yRotation))
                                        );

                                        Matrix3f zAxisRotationMatrix = new Matrix3f(
                                                new Vector3f((float) Math.cos(zRotation), (float) -Math.sin(zRotation), 0),
                                                new Vector3f((float) Math.sin(zRotation), (float) Math.cos(zRotation), 0),
                                                new Vector3f(0, 0, 1)
                                        );

                                        for (int i = 0; i < 200; i++) {
                                            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);
                                            parent.rotation.y -= yRotationDeg;
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 700; i++) {
                                            parent.percentRotation = zAxisRotationMatrix.multiplyByVector(parent.percentRotation);
                                            parent.rotation.z -= zRotationDeg;
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        yAxisRotationMatrix = new Matrix3f(
                                                new Vector3f((float) Math.cos(-yRotation), 0, (float) Math.sin(-yRotation)),
                                                new Vector3f(0, 1, 0),
                                                new Vector3f((float) -Math.sin(-yRotation), 0, (float) Math.cos(-yRotation))
                                        );

                                        for (int i = 0; i < 200; i++) {
                                            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);
                                            parent.rotation.y += yRotationDeg;
                                            Thread.sleep(10);
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        System.exit(1);
                                    }

                                }

                            }))
                            .createModules();

                    break;

                case 2:

                    new GameObject(positions[i], new Vector3f(0, 180, 0))
                            .addModule(new RenderModule("sphere2"))
                            .addModule(new SpotLightSource(new Vector4f(0, 0, 255, 400), turnableColorLights[1])
                                    .setSpecularLighting(false))
                            .addModule(new InteractionModule(true, 20, Keyboard.KEY_M, "move", 10, new AsyncInteraction() {

                                public void interact() {

                                    try {

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0.05f, 0, 0)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, -0.05f, 0)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(-0.05f, 0, 0)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0.05f, 0)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, 0.05f)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 400; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, -0.05f)});
                                            Thread.sleep(10);
                                        }

                                        Thread.sleep(200);

                                        for (int i = 0; i < 200; i++) {
                                            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, 0.05f)});
                                            Thread.sleep(10);
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        System.exit(1);
                                    }

                                }

                            }))
                            .createModules();

                    break;

            }

            types[type] = true;

        }

        colorLights[0] = (int) MathHelper.clamp(colorLights[0], 0, 8);

        for (int i = 0; i < colorLights[0]; i++) {

            Vector3f position = new Vector3f(
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 35,
                    25 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 10,
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 35
            );

            Vector4f color = new Vector4f(
                    Randomizer.getRandomInt(0, 255),
                    Randomizer.getRandomInt(0, 255),
                    Randomizer.getRandomInt(0, 255),
                    800 / colorLights[0]
            );

            new GameObject(position, new Vector3f(90, 0, 0))
                    .addModule(new RenderModule("sphere2"))
                    .addModule(new SpotLightSource(color, colorLights[1]))
                    .createModules();

        }

        moveableLights[0] = (int) MathHelper.clamp(moveableLights[0], 0, 8);

        for (int i = 0; i < moveableLights[0]; i++) {

            Vector3f position = new Vector3f(
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 35,
                    25 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 10,
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 35
            );

            new GameObject(position, new Vector3f(90, 0, 0))
                    .addModule(new RenderModule("sphere2"))
                    .addModule(new SpotLightSource(new Vector4f(255, 255, 255, 600 / moveableLights[0]), moveableLights[1]))
                    .addModule(new InteractionModule(true, 10, Keyboard.KEY_M, "move", new AsyncInteraction() {
                        @Override
                        public void interact() {

                            try {

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0.05f, 0, 0)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, -0.05f, 0)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(-0.05f, 0, 0)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0.05f, 0)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, 0.05f)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 800; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, -0.05f)});
                                    Thread.sleep(10);
                                }

                                Thread.sleep(200);

                                for (int i = 0; i < 400; i++) {
                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, 0, 0.05f)});
                                    Thread.sleep(10);
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                System.exit(1);
                            }

                        }
                    }))
                    .createModules();

        }

        for (int i = 0; i < spheres1; i++) {

            new GameObject(new Vector3f(20 + ((i % 2 == 0) ? 2.5f * i : -2.5f * i), 10 + Randomizer.getRandomInt(-1, 1) * 5, 30), new Vector3f())
                    .addModule(new RenderModule("sphere"))
                    .createModules();

        }

        for (int i = 0; i < monkeys1; i++) {

            new GameObject(new Vector3f(20 + ((i % 2 == 0) ? 5f * i : -5f * i), 2.5f, 30 + Randomizer.getRandomInt(-2, 5) * 5), new Vector3f())
                    .addModule(new RenderModule("rotatedMonkey"))
                    .createModules();

        }

        for (int i = 0; i < spheres2; i++) {

            Vector3f position = new Vector3f(
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 40,
                    ((float) Randomizer.getRandomInt(0, 100) / 100f) * 15,
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 40
            );

            new GameObject(position, new Vector3f())
                    .addModule(new RenderModule("sphere"))
                    .createModules();

        }

        for (int i = 0; i < monkeys2; i++) {

            String renderName = (Randomizer.getRandomInt(0, 1) == 0) ? "monkey" : "rotatedMonkey";

            Vector3f position = new Vector3f(
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 40,
                    ((float) Randomizer.getRandomInt(0, 100) / 100f) * 15,
                    -45 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 40
            );

            new GameObject(position, new Vector3f())
                    .addModule(new RenderModule(renderName))
                    .createModules();

        }

        for (int i = 0; i < objects3; i++) {

            String renderName = (Randomizer.getRandomInt(0, 1) == 0) ? "sphere" : "rotatedMonkey";

            Vector3f position = new Vector3f(
                    -10 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 20,
                    14f + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 22,
                    50 + ((float) Randomizer.getRandomInt(0, 100) / 100f) * 10
            );

            new GameObject(position, new Vector3f())
                    .addModule(new RenderModule(renderName))
                    .createModules();

        }

    }

}
