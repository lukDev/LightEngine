/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable;

import lightEngine.gameObjects.modules.gui.GUIElement;
import lightEngine.graphics.Renderer;
import lightEngine.graphics.gui.GUIScreen;
import lightEngine.graphics.renderable.materials.Material2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class LoadingScreen extends GUIScreen {

    boolean active = true;
    private Material2D material;
    private List<Vector2f> vertices;
    private List<Vector2f> uvs;

    /**
     * Default constructor for loading screens.
     *
     * @param backgroundTextureName The background texture of the loading screen.
     */
    public LoadingScreen(String backgroundTextureName) {

        super("loadingStarted", "loadingStopped");

        material = new Material2D();
        material.setTextureName(backgroundTextureName);
        vertices = new ArrayList<Vector2f>();
        uvs = new ArrayList<>();

        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    /**
     * Calculates size and position (centered).
     */
    private void calculateVertexPositions() {

        int ox = material.getTexture().getTexture().getImageWidth() / 2; //Offset x
        int oy = material.getTexture().getTexture().getImageHeight() / 2; //Offset y

        int cx = Display.getWidth() / 2; //Center x
        int cy = Display.getHeight() / 2; //Center y

        float pox = (float) ox / cx; //x offset in percent
        float poy = (float) oy / cy; //y offset in percent

        vertices.add(new Vector2f(-pox, -poy));
        vertices.add(new Vector2f(pox, -poy));
        vertices.add(new Vector2f(pox, poy));
        vertices.add(new Vector2f(-pox, poy));

    }

    public void render() {

        if (active) {
            if (material.getTexture() == null) {
                material.setTextureFromName();
                calculateVertexPositions();
            }

            Renderer.renderObject2D(vertices, uvs, material, Renderer.RENDER_QUADS);
            elements.forEach(GUIElement::render);
        }

    }

}