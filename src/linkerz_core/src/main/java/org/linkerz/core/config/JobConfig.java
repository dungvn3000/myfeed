/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.config;

/**
 * The Class JobConfig.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 11:23 PM
 */
public abstract class JobConfig implements Config {
    /**
     * Default is one
     */
    private int numberOfHandler = 1;


    public int getNumberOfHandler() {
        return numberOfHandler;
    }

    public void setNumberOfHandler(int numberOfHandler) {
        this.numberOfHandler = numberOfHandler;
    }
}
