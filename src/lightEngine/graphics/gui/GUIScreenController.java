/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.gui;

import java.util.ArrayList;
import java.util.List;

public class GUIScreenController {

    private static List<GUIScreen> guiScreens = new ArrayList<GUIScreen>();

    public static void addGUIScreen(GUIScreen screen) {
        guiScreens.add(screen);
    }

    public static boolean isScreenActive(GUIScreen screen) {
        return screen != null && screen.active;
    }

}
