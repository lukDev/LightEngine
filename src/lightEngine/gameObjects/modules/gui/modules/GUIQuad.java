/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.gui.modules;

import lightEngine.graphics.Renderer;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GUIQuad extends GUIModule {

    private List<Vector2f> uvs;

    public GUIQuad() {

        uvs = new ArrayList<>();
        uvs.add(new Vector2f(0, 1));
        uvs.add(new Vector2f(1, 1));
        uvs.add(new Vector2f(1, 0));
        uvs.add(new Vector2f(0, 0));

    }

    public void render() {

        super.render();

        if (verticesToRender.size() == 0) {

            Vector2f position = parent.getPosition();
            Vector2f size = parent.getSize();

            verticesToRender.add(new Vector2f(position.x, position.y + size.y));
            verticesToRender.add(new Vector2f(position.x + size.x, position.y + size.y));
            verticesToRender.add(new Vector2f(position.x + size.x, position.y));
            verticesToRender.add(new Vector2f(position.x, position.y));

        }

        Renderer.renderObject2D(verticesToRender, uvs, parent.material, Renderer.RENDER_QUADS);

    }

}