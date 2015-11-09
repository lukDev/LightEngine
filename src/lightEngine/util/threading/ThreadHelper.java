/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.threading;

import java.util.ArrayList;
import java.util.List;

public class ThreadHelper {

    private static List<Thread> threads = new ArrayList<Thread>();

    /**
     * Runs a thread asynchronously.
     *
     * @param thread The thread (Runnable) to run.
     * @param name   The name of the thread.
     */
    public static void startThread(Runnable thread, String name) {

        //Adds the desired thread to the list and starts it
        Thread newThread;

        if (name != null) newThread = new Thread(thread, name);
        else newThread = new Thread(thread);

        threads.add(newThread);
        newThread.start();

    }

    /**
     * Runs a thread asynchronously.
     * A name will automatically be assigned.
     *
     * @param thread The thread (Runnable) to run.
     */
    public static void startThread(Runnable thread) {
        startThread(thread, null);
    }

    /**
     * Stops all threads that were run via startThread().
     * Note that all mengine threads will be stopped as well.
     */
    public static void stopAllThreads() {
        threads.forEach(Thread::interrupt);
    }

}
