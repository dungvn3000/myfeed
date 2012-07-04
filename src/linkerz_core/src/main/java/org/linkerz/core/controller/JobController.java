/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.controller;

import com.hazelcast.core.HazelcastInstance;
import org.linkerz.core.handler.Handler;

import java.util.List;

/**
 * The Class Controller.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 2:13 PM
 */
public interface JobController {

    /**
     * Start controlling.
     */
    void start();

    /**
     * Set handlers.
     * @param handlers
     */
    void setHandlers(List<Handler> handlers);


    /**
     * Set HazelCast instance.
     * @param hazelcastInstance
     */
    void setHazelcastInstance(HazelcastInstance hazelcastInstance);
}
