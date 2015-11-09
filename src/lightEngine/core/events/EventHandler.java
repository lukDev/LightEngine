/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.core.events;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 04.10.2014 00:06
 */
public interface EventHandler {

    /**
     * The action that should be performed once the bound event is triggered.
     */
    void handle();

}
