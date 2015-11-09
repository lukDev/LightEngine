/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.gui.modules;

import lightEngine.graphics.Renderer;
import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.*;

public class GUIEllipse extends GUIModule {

    public void render() {

        super.render();

        if (verticesToRender.size() == 0) {

            verticesToRender.add(new Vector2f(parent.getPosition().x, parent.getPosition().y));

            for (int i = 360; i > 0; i--) {

                float radians = (float) toRadians(i);

                verticesToRender.add(new Vector2f(
                  parent.getPosition().x + ((float) cos(radians) * parent.getSize().x),
                  parent.getPosition().y + ((float) sin(radians) * parent.getSize().y)
                ));

            }

            verticesToRender.add(new Vector2f(
              parent.getPosition().x + ((float) cos(360) * parent.getSize().x),
              parent.getPosition().y + ((float) sin(360) * parent.getSize().y)
            ));

        }

        Renderer.renderObject2D(verticesToRender, Renderer.RENDER_TRIANGLE_FAN);

    }

}
