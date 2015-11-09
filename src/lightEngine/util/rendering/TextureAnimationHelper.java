/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.rendering;

import lightEngine.graphics.renderable.animations.TextureAnimation;
import lightEngine.graphics.renderable.animations.TextureKeyFrame;
import lightEngine.util.resources.ResourceHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureAnimationHelper {

    /**
     * Returns the desired texture animation.
     *
     * @param animationName The name of the desired animation.
     * @return The desired animation.
     * @throws java.io.IOException if the animation file is not found.
     */
    public static TextureAnimation getAnimation(String animationName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(animationName, ResourceHelper.RES_TEXTURE_ANIMATED)));
        List<TextureKeyFrame> frames = new ArrayList<TextureKeyFrame>();
        String line;
        boolean stopAfterOneCycle = false;

        while ((line = reader.readLine()) != null) {

            if (line.startsWith("f ")) {

                int frameDelay;
                String frameName = line.split(" ")[1];
                if (line.split(" ")[2] != null) frameDelay = Integer.parseInt(line.split(" ")[2]);
                else frameDelay = 20;

                frames.add(new TextureKeyFrame(frameDelay, TextureHelper.getTexture(frameName).getTexture()));

            } else if (line.startsWith("r ")) {
                if (!line.split(" ")[1].equals("true"))
                    stopAfterOneCycle = true; //r stands for repeat which makes it the opposite of stopAfterOneCycle
            }

        }

        reader.close();
        return new TextureAnimation(frames.toArray(new TextureKeyFrame[frames.size()]), stopAfterOneCycle);

    }

}
