/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.renderable.light;

import org.lwjgl.util.vector.Vector4f;

public class GlobalLightSource extends LightSource {

    public GlobalLightSource(float strength) {
        super(new Vector4f(255, 255, 255, strength));
    }

    public GlobalLightSource(Vector4f color) {
        super(color);
    }

}
