/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.data;

public class ArrayHelper {

    /**
     * Fills An array with zeros
     *
     * @param arrayLength The length of the desired array
     * @return The filled array
     */
    public static double[] fillArray(int arrayLength) {
        return fillArray(arrayLength, 0);
    }

    /**
     * Fills an array with custom numbers
     *
     * @param arrayLength The length of the desired array
     * @param placeholder The desired number to fill the array with
     * @return The filled array
     */
    public static double[] fillArray(int arrayLength, double placeholder) {

        double[] output = new double[arrayLength];
        for (int i = 0; i < arrayLength; i++) output[i] = placeholder;
        return output;

    }

}
