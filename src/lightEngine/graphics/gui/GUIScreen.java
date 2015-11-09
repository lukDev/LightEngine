/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.gui;

import lightEngine.core.ObjectController;
import lightEngine.core.events.EventController;
import lightEngine.gameObjects.modules.gui.GUIElement;

import java.util.ArrayList;
import java.util.List;

public class GUIScreen {

    protected List<GUIElement> elements = new ArrayList<>();
    boolean active;

    public GUIScreen(String activationEvent, String deactivationEvent) {
        this();
        EventController.addEventHandler(activationEvent, () -> active = true);
        EventController.addEventHandler(deactivationEvent, () -> active = false);
    }

    public GUIScreen(boolean active) {
        this();
        this.active = active;
    }

    public GUIScreen(String activationEvent, String deactivationEvent, boolean active) {
        this(activationEvent, deactivationEvent);
        this.active = active;
    }

    private GUIScreen() {
        ObjectController.addGuiScreen(this);
    }

    public void addElement(GUIElement element) {
        elements.add(element);
    }

    public List<GUIElement> getElements() {
        return elements;
    }
}
