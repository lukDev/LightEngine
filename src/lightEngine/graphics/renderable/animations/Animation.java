/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Animation {

    protected List<KeyFrame> keyFrames;
    private int currentKeyFrameIndex;
    private boolean stopAfterOneCycle;
    private boolean running = false;

    /**
     * Create an animation from key frames
     *
     * @param keyFrames         The array of key frames in the animation
     * @param stopAfterOneCycle If true, the animation will only run once. If false, it will run continuously
     */
    public Animation(KeyFrame[] keyFrames, boolean stopAfterOneCycle) {

        this.keyFrames = new ArrayList<>();
        Collections.addAll(this.keyFrames, keyFrames);
        if (!this.keyFrames.isEmpty()) currentKeyFrameIndex = 0;
        this.stopAfterOneCycle = stopAfterOneCycle;
        start();

    }

    /**
     * Start the animation or resume it if it was previously paused
     */
    public void start() {
        running = true;
    }

    /**
     * Pause the animation.
     * It will start from the last key frame the next time you start it
     */
    public void pause() {
        running = false;
    }

    /**
     * Stop the animation.
     * It will start from the beginning the next time you start it
     */
    public void stop() {
        if (!keyFrames.isEmpty()) currentKeyFrameIndex = 0;
        running = false;
    }

    /**
     * Get the currently active key frame
     *
     * @return The current key frame
     */
    public KeyFrame getCurrentKeyFrame() {

        if (running) {

            if (!keyFrames.get(currentKeyFrameIndex).isActive()) keyFrames.get(currentKeyFrameIndex).setActive();
            if (keyFrames.get(currentKeyFrameIndex).isDone()) {

                if (!stopAfterOneCycle) {
                    if (currentKeyFrameIndex == keyFrames.size() - 1) currentKeyFrameIndex = 0;
                    else currentKeyFrameIndex++;
                    keyFrames.get(currentKeyFrameIndex).setActive(); //To reset the timer once it has stopped
                } else if (currentKeyFrameIndex < keyFrames.size() - 1) currentKeyFrameIndex++;

            }

        }

        return keyFrames.get(currentKeyFrameIndex);

    }

}

