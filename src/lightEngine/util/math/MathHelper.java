/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.math;

public class MathHelper {

    /**
     * Ensures that a number is between two thresholds.
     *
     * @param input The number to clamp.
     * @param min   The minimum value {@code input} is allowed to have.
     * @param max   The maximum value {@code input} is allowed to have.
     * @return {@code input}, if it is between the two values. Otherwise  {@code min} or  {@code max} is returned respectively.
     */
    public static double clamp(double input, double min, double max) {
        if (input < min) input = min;
        if (input > max) input = max;
        return input;
    }

    /**
     * Ensures that a number is bigger than a given value.
     *
     * @param input The number to clamp.
     * @param min   The minimum value {@code input} is allowed to have.
     * @return {@code input}, if it is bigger than {@code min}. Otherwise  {@code min} is returned.
     */
    public static double clampMin(double input, double min) {
        if (input < min) return min;
        else return input;
    }

    /**
     * Ensures that a number is smaller than a given value.
     *
     * @param input The number to clamp.
     * @param max   The maximum value {@code input} is allowed to have.
     * @return {@code input}, if it is smaller than {@code max}. Otherwise  {@code max} is returned.
     */
    public static double clampMax(double input, double max) {
        if (input > max) return max;
        else return input;
    }

}
