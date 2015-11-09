/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.textures;

public class StaticTexture implements Texture {

    org.newdawn.slick.opengl.Texture texture;

    public StaticTexture(org.newdawn.slick.opengl.Texture texture) {

        this.texture = texture;

    }

    @Override
    public org.newdawn.slick.opengl.Texture getTexture() {
        return texture;
    }

}
