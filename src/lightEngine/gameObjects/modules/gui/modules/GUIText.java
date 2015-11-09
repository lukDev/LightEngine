/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.gui.modules;

import lightEngine.util.resources.FontHelper;
import lightEngine.util.resources.PreferenceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class GUIText extends GUIModule {

    public String text;
    public org.newdawn.slick.Color color;
    protected String fontFace;
    protected int fontStyle;
    protected int fontSize;
    protected TrueTypeFont font;

    public GUIText(String text, int fontSize) {

        this(text, Font.PLAIN, fontSize);

    }

    public GUIText(String text, int fontStyle, int fontSize) {

        this(text, PreferenceHelper.getValue("defaultFontFace"), fontStyle, fontSize);

    }

    public GUIText(String text, String fontFace, int fontStyle, int fontSize) {

        this.text = text;
        this.fontFace = fontFace;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;

    }

    public GUIText setColor(org.newdawn.slick.Color color) {

        this.color = color;
        return this;

    }

    public void render() {

        super.render();

        if (font == null)
            font = FontHelper.loadFont(fontFace, fontStyle, fontSize, PreferenceHelper.getBoolean("antiAliasing"));

        org.newdawn.slick.Color temporaryColor;

        if (color != null) temporaryColor = color;
        else temporaryColor = parent.material.getColor();

        Vector2f position = parent.getPosition();

        font.drawString(position.x, position.y, text, temporaryColor);

    }

    public void onExternalUpdate(Object[] args) {

        if (args.length >= 1) this.text = (String) args[0];
        if (args.length == 2) onExternalUpdate((Integer) args[1]);
        if (args.length == 3) onExternalUpdate((Integer) args[1], (Integer) args[2]);
        if (args.length == 4) onExternalUpdate((Integer) args[1], (Integer) args[2], (String) args[3]);
        if (args.length == 5) onExternalUpdate((Integer) args[1], (Integer) args[2], (String) args[3], (Color) args[4]);

    }

    protected void onExternalUpdate(int fontSize) {

        onExternalUpdate(Font.PLAIN, fontSize);

    }

    protected void onExternalUpdate(int fontStyle, int fontSize) {

        onExternalUpdate(fontStyle, fontSize, PreferenceHelper.getValue("defaultFontFace"));

    }

    protected void onExternalUpdate(int fontStyle, int fontSize, String fontFace) {

        onExternalUpdate(fontStyle, fontSize, fontFace, Color.white);

    }

    protected void onExternalUpdate(int fontStyle, int fontSize, String fontFace, Color color) {

        font = FontHelper.loadFont(fontFace, fontStyle, fontSize, PreferenceHelper.getBoolean("antiAliasing"));
        //parent.material.setColor(color);

    }

    @Override
    public void onSave() {

        super.onSave();
        font = null;

    }

}
