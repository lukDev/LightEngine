/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.math;

import java.text.DecimalFormat;

public class NumberFormatHelper {

    /**
     * Formats a number in a specified pattern.
     *
     * @param input  The number to format.
     * @param format The formatting pattern, e.g. "0.00" (Number with 2 decimal digits).
     * @return The formatted number.
     */
    public static String format(double input, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(input);
    }

    /**
     * Cuts the decimals of a number to a specified amount.
     *
     * @param input    The number to format.
     * @param decimals The number of decimals to keep.
     * @return The formatted number.
     */
    public static String cutDecimals(double input, int decimals) {

        String format;
        if (decimals == 0) format = "0";
        else {
            format = "0.";
            for (int i = 0; i < decimals; i++) {
                format = format + "0";
            }
        }

        return format(input, format);

    }

}
