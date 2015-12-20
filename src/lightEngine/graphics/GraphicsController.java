/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics;

import lightEngine.util.data.ColorHelper;
import lightEngine.util.input.Input;
import lightEngine.util.input.InputEventType;
import lightEngine.util.resources.PreferenceHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector4f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GraphicsController {

    public static Dimension currentRenderDimension;
    public static boolean isFullscreen;
    public static boolean mEnchmarkEnabled;
    public static int renderDistance;
    public static boolean isBlackAndWhite = false;
    public static boolean wasResized = false;
    public static int fieldOfView;
    public static Vector4f skyColor;
    private static int width;
    private static int height;
    private static String title;
    private static int fps;
    public static int shadowMapToDisplay = -1;
    public static boolean shadowMapFullScreen = false;

    /**
     * Creates a new window with OpenGL context, loads all graphics related preferences.
     */
    public static void createDisplay() {

        width = PreferenceHelper.getInteger("screenWidth");
        height = PreferenceHelper.getInteger("screenHeight");
        renderDistance = PreferenceHelper.getInteger("renderDistance");
        fieldOfView = PreferenceHelper.getInteger("fieldOfView");
        skyColor = ColorHelper.colorToRgba(ColorHelper.getHexColor(PreferenceHelper.getValue("skyColor")));

        fps = PreferenceHelper.getInteger("framesPerSecond");

        mEnchmarkEnabled = PreferenceHelper.getBoolean("mEnchmarkEnabled");
        title = PreferenceHelper.getValue("title");

        if (!PreferenceHelper.getBoolean("fullscreen")) {

            setupWindow(width, height, title);

        } else {

            setupFullscreen();

        }

        try {

            Mouse.create();
            Input.assignInputEvent("fullscreen", true, InputEventType.ACTIVATED, Keyboard.KEY_F11);

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        Input.assignInputEvent("shadowMap-1", true, InputEventType.ACTIVATED, Keyboard.KEY_0);
        Input.assignInputEvent("shadowMap0", true, InputEventType.ACTIVATED, Keyboard.KEY_1);
        Input.assignInputEvent("shadowMap1", true, InputEventType.ACTIVATED, Keyboard.KEY_2);
        Input.assignInputEvent("shadowMap2", true, InputEventType.ACTIVATED, Keyboard.KEY_3);
        Input.assignInputEvent("shadowMap3", true, InputEventType.ACTIVATED, Keyboard.KEY_4);
        Input.assignInputEvent("shadowMap4", true, InputEventType.ACTIVATED, Keyboard.KEY_5);
        Input.assignInputEvent("shadowMap5", true, InputEventType.ACTIVATED, Keyboard.KEY_6);
        Input.assignInputEvent("shadowMap6", true, InputEventType.ACTIVATED, Keyboard.KEY_7);
        Input.assignInputEvent("shadowMap7", true, InputEventType.ACTIVATED, Keyboard.KEY_8);
        Input.assignInputEvent("shadowMapFull", true, InputEventType.ACTIVATED, Keyboard.KEY_P);

        initializeOpenGL();

    }

    /**
     * Used in the render loop, it clears the screen every frame and sets the sky color.
     */
    public static void clearScreen() {

        glClearColor(skyColor.x, skyColor.y, skyColor.z, skyColor.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    /**
     * Called every frame by the render loop.
     * It detects, if the user has resized the window or wants to toggle fullscreen mode.
     * It also updates and synchronizes the window.
     */
    public static void update() {

        if (Input.inputEventTriggered("fullscreen")) {

            wasResized = true;
            if (isFullscreen) setupWindow(width, height, title);
            else setupFullscreen();

        } else wasResized = false;

        if (Input.inputEventTriggered("shadowMap-1"))
            shadowMapToDisplay = -1;

        if (Input.inputEventTriggered("shadowMap0"))
            shadowMapToDisplay = 0;

        if (Input.inputEventTriggered("shadowMap1"))
            shadowMapToDisplay = 1;

        if (Input.inputEventTriggered("shadowMap2"))
            shadowMapToDisplay = 2;

        if (Input.inputEventTriggered("shadowMap3"))
            shadowMapToDisplay = 3;

        if (Input.inputEventTriggered("shadowMap4"))
            shadowMapToDisplay = 4;

        if (Input.inputEventTriggered("shadowMap5"))
            shadowMapToDisplay = 5;

        if (Input.inputEventTriggered("shadowMap6"))
            shadowMapToDisplay = 6;

        if (Input.inputEventTriggered("shadowMap7"))
            shadowMapToDisplay = 7;

        if (Input.inputEventTriggered("shadowMapFull"))
            shadowMapFullScreen = !shadowMapFullScreen;

        Display.update();
        if (!mEnchmarkEnabled) Display.sync(fps);

    }

    /**
     * Configures the window.
     *
     * @param width  Window width
     * @param height Window height
     * @param title  Window title
     */
    private static void setupWindow(int width, int height, String title) {

        try {

            Display.setDisplayMode(new org.lwjgl.opengl.DisplayMode(width, height));
            Display.setTitle(title);
            Display.setFullscreen(false);

            if (!Display.isCreated()) Display.create(new PixelFormat(8, 8, 0, 8));

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        isFullscreen = false;

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_BLEND);

        glEnable(GL_TEXTURE_2D);

    }

    /**
     * Changes the window to be a fullscreen one.
     */
    private static void setupFullscreen() {

        try {

            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            if (!Display.isCreated()) Display.create(new PixelFormat(8, 8, 0, 8));

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        isFullscreen = true;

    }

    /**
     * Allows you to make the game a part of another window.
     *
     * @param parent The AWT canvas you want the game to show in
     */
    public static void setParent(Canvas parent) {

        try {
            Display.setParent(parent);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Takes a screenshot and saves it in the screenshots/ folder.
     */
    public static void takeScreenshot() {

        int width = Display.getWidth();
        int height = Display.getHeight();
        int bytesPerPixel = 4; //1 byte per r/g/b/a value

        glReadBuffer(GL_FRONT);
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bytesPerPixel);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String saveFileName = format.format(date);

        File file = new File("screenshots/" + saveFileName + ".png");
        String fileFormat = "PNG";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                //Stuff 'n' things for every pixel
                int i = (x + (width * y)) * bytesPerPixel;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);

            }

        }

        try {
            ImageIO.write(image, fileFormat, file); //Write the file
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Changes the title of the game window.
     *
     * @param title The desired title
     */
    public static void setWindowTitle(String title) {
        Display.setTitle(title);
    }

    /**
     * Returns the current game window width.
     *
     * @return The current game window width
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Returns the current game window height.
     *
     * @return The current game window height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Returns the maximum frames per second specified in the properties.
     *
     * @return The maximum FPS value
     */
    public static int getFps() {
        return fps;
    }

    /**
     * Returns the game window's current aspect ratio.
     *
     * @return The game window's current aspect ratio
     */
    public static float getAspectRatio() {
        return (float) width / height;
    }

    /**
     * Configures OpenGL to look nice.
     */
    private static void initializeOpenGL() {

        glShadeModel(GL_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    /**
     * Used for displaying 2D content, such as GUI.
     * Called in the render queue.
     */
    public static void switchTo2D() {

        if (currentRenderDimension != Dimension.DIM_2) {

            glMatrixMode(GL_MODELVIEW);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glMatrixMode(GL_MODELVIEW);

            glPushMatrix();
            glLoadIdentity();

            currentRenderDimension = Dimension.DIM_2;

        }

    }

    /**
     * Used for displaying 3D content.
     * Called in the render queue.
     */
    public static void switchTo3D() {

        if (currentRenderDimension != Dimension.DIM_3) {

            glPopMatrix(); //From 2D
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluPerspective(fieldOfView, (float) Display.getWidth() / Display.getHeight(), 0.1f, renderDistance);
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glMatrixMode(GL_MODELVIEW);

            currentRenderDimension = Dimension.DIM_3;

        }

    }

}
