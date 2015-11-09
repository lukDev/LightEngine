/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.time;

public class TimeHelper {

    public static boolean oneSecondPassed = false;
    public static float deltaTime;
    public static int FPS;
    public static int highestFPS;
    public static int TPS;
    public static int highestTPS;
    public static float timeModifier = 1;
    private static float lastTime = System.nanoTime() / 1000000;
    private static float lastFPS;
    private static long currentFPS;
    private static float lastTPS;
    private static long currentTPS;

    /**
     * Sets the lastTime variables to the current time so they aren't 0.
     * Only needed once.
     */
    public static void initialize() {

        lastTime = getTime();
        lastFPS = getTime();
        lastTPS = getTime();

    }

    /**
     * Gives you the current time.
     *
     * @return Current time in milliseconds.
     */
    public static long getTime() {
        return System.nanoTime() / 1000000; //Nanoseconds give more accurate millisecond values
    }

    /**
     * Updates the deltaTime variable.
     */
    public static void updateDeltaTime() {

        float thisTime = getTime();
        deltaTime = thisTime - lastTime;
        deltaTime *= timeModifier;
        lastTime = thisTime;

    }

    /**
     * Updates the currentFPS and lastFPS variables.
     */
    public static void updateFPS() {

        oneSecondPassed = false;
        if (getTime() - lastFPS > 1000) {

            oneSecondPassed = true;
            FPS = (int) currentFPS;
            if (currentFPS > highestFPS) highestFPS = FPS;
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

    /**
     * Updates the currentTPS and lastTPS variables (ticks per second).
     */
    public static void updateTPS() {

        if (getTime() - lastTPS > 1000) {

            TPS = (int) currentTPS;
            if (currentTPS > highestTPS) highestTPS = TPS;
            currentTPS = 0;
            lastTPS += 1000;

        }

        currentTPS++;

    }

    /**
     * Pauses the thread this method was called from by the amount of milliseconds.
     *
     * @param milliseconds The amount of milliseconds the pause should last.
     */
    public static void sleep(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
