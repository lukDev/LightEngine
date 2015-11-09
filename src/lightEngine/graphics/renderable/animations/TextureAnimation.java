/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.animations;

import org.newdawn.slick.opengl.Texture;

public class TextureAnimation extends Animation {

    /**
     * Initializes a new texture animation.
     *
     * @param keyFrames         The keyframes for this animation.
     * @param stopAfterOneCycle Should the animation stop or run continuously?
     */
    public TextureAnimation(TextureKeyFrame[] keyFrames, boolean stopAfterOneCycle) {
        super(keyFrames, stopAfterOneCycle);
    }

    /**
     * Returns the currently active texture.
     *
     * @return The current texture.
     */
    public Texture getCurrentTexture() {

        return (Texture) getCurrentKeyFrame().getData();

    }

}
