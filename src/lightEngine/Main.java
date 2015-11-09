package lightEngine;

import lightEngine.core.GameController;
import lightEngine.core.ObjectController;
import lightEngine.core.Setup;
import lightEngine.gameObjects.GameObject;
import lightEngine.gameObjects.modules.Module;
import lightEngine.gameObjects.modules.controls.ControllerManual;
import lightEngine.gameObjects.modules.interaction.AsyncInteraction;
import lightEngine.gameObjects.modules.interaction.InteractionModule;
import lightEngine.gameObjects.modules.physics.MovementModule;
import lightEngine.gameObjects.modules.renderable.Camera;
import lightEngine.gameObjects.modules.renderable.RenderModule;
import lightEngine.gameObjects.modules.renderable.light.DirectionalLightSource;
import lightEngine.gameObjects.modules.renderable.light.SpotLightSource;
import lightEngine.util.math.vectors.Matrix3f;
import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Main {

    public static void main(String[] args) {

        Setup.setupDefaults();

        //GameObject Time ;)
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

        new GameObject(new Vector3f(0, 2.5f, -20), new Vector3f())
                .addModule(new RenderModule("monkey"))
                .addModule(new InteractionModule(true, 10, Keyboard.KEY_M, "move down", new AsyncInteraction() {
                    @Override
                    public void interact() {
                        parent.position = VectorHelper.sumVectors(new Vector3f[] {parent.position, new Vector3f(0, -0.01f, 0)});
                    }
                }))
                .createModules();

        new GameObject(new Vector3f(20, 10, 30), new Vector3f())
                .addModule(new RenderModule("sphere"))
                .createModules();

        new GameObject(new Vector3f(10, 15, 30), new Vector3f())
                .addModule(new RenderModule("sphere"))
                .createModules();

        //Lights

        //Directional Light
        /*new GameObject(new Vector3f(35, 30, 0), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new DirectionalLightSource(300))
                .createModules();*/

        //Basic Light
        new GameObject(new Vector3f(15, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 500), 40)
                        .setSpecularLighting(false))
                .createModules();

        /*new GameObject(new Vector3f(0, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(20, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(10, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(5, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(25, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(-5, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();

        new GameObject(new Vector3f(-10, 30, 10), new Vector3f(45, 180, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(
                        new Vector4f(255, 255, 255, 100), 40)
                        .setSpecularLighting(false))
                .createModules();*/

        //Spot Lights
        /*new GameObject(new Vector3f(0, 20, 40), new Vector3f(0, 0, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(255, 0, 0, 400), 25)
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

        new GameObject(new Vector3f(-7.5f, 30, 40), new Vector3f(0, 0, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(0, 255, 0, 400), 25)
                        .setSpecularLighting(false))
                .addModule(new InteractionModule(true, 20, Keyboard.KEY_R, "rotate", 10, new AsyncInteraction() {

                    public void interact() {

                        try {

                            float yRotation = (float) Math.toRadians(-0.15);
                            float zRotation = (float) Math.toRadians(-(360f / 700f));

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
                                Thread.sleep(10);
                            }

                            Thread.sleep(200);

                            for (int i = 0; i < 700; i++) {
                                parent.percentRotation = zAxisRotationMatrix.multiplyByVector(parent.percentRotation);
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
                                Thread.sleep(10);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                    }

                }))
                .createModules();

        new GameObject(new Vector3f(7.5f, 30, 40), new Vector3f(0, 0, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(0, 0, 255, 400), 25)
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
                .createModules();*/

        //Specular Lighting Demo
        /*new GameObject(new Vector3f(-35, 30, -35), new Vector3f())
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(255, 255, 0, 100)))
                .createModules();

        new GameObject(new Vector3f(-40, 25, -40), new Vector3f())
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(0, 255, 0, 100)))
                .createModules();

        new GameObject(new Vector3f(-30, 25, -30), new Vector3f(90, 0, 0))
                .addModule(new RenderModule("sphere2"))
                .addModule(new SpotLightSource(new Vector4f(255, 0, 0, 100), 80))
                .createModules();*/

        GameController.setLoading(false);

    }

}
