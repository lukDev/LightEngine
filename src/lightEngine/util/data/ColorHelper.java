/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.data;

import org.lwjgl.util.vector.Vector4f;

import java.awt.*;

public class ColorHelper {

    /**
     * Converts a string containing hex code to an AWT color
     *
     * @param hex The hex code (e.g. #FFF, #1659de, 000, 24351a)
     * @return The resulting color
     */
    public static Color getHexColor(String hex) {
        if (hex.startsWith("#")) hex = hex.replace("#", "");

        //Conversion to long notation
        if (hex.length() == 3) {
            char r = hex.charAt(0);
            char g = hex.charAt(1);
            char b = hex.charAt(2);

            hex = new String(new char[]{r, r, g, g, b, b});
        }

        //Conversion to int
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new Color(r, g, b);

    }

    /**
     * Converts a Java AWT color to an RGBA value
     *
     * @param color The color to convert
     * @return A Vector4f containing the rgba value (x = r, y = g, z = b, w = a)
     */
    public static Vector4f colorToRgba(Color color) {
        return new Vector4f((float) color.getRed() / 256,
          (float) color.getGreen() / 256,
          (float) color.getBlue() / 256,
          (float) color.getAlpha() / 256);
    }

}
