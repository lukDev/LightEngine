/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.time;

public class Timer {

    private int tickTime;
    private long startTime;
    private boolean running = false;

    /**
     * Initializes a new timer.
     *
     * @param tickTime The time the timer should last for.
     */
    public Timer(int tickTime) {
        this.tickTime = tickTime;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        startTime = TimeHelper.getTime();
        running = true;
    }

    /**
     * Determines if the timer is already running.
     *
     * @return True if the timer is already running, false if not.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Determines if the timer is done.
     *
     * @return True if done, false if running.
     */
    public boolean isDone() {
        return tickTime <= TimeHelper.getTime() - startTime;
    }

}
