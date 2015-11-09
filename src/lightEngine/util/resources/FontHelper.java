/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.resources;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class FontHelper {

    /**
     * Loads a true type font.
     *
     * @param face         The font face / type.
     * @param style        The style (bold|italic|...).
     * @param size         The font size.
     * @param antiAliasing Should anti aliasing be applied to this font?
     * @return The true type font.
     */
    public static TrueTypeFont loadFont(String face, int style, int size, boolean antiAliasing) {

        Font fontTemplate = new Font(face, style, size);
        return new TrueTypeFont(fontTemplate, antiAliasing);

    }

}
