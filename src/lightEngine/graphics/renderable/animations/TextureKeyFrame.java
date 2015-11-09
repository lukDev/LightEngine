/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.animations;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

public class TextureKeyFrame extends KeyFrame {

    /**
     * A keyframe containing a texture.
     *
     * @param delay   The duration of this keyframe.
     * @param texture The texture of this keyframe.
     */
    public TextureKeyFrame(int delay, Texture texture) {
        super(delay, texture);
    }

    /**
     * Returns the current texture.
     *
     * @return The current texture.
     */
    @Override
    public Texture getData() {
        return (TextureImpl) data;
    }

}
