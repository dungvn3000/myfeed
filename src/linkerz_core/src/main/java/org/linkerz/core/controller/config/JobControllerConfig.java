/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.controller.config;

import org.linkerz.core.config.Config;

/**
 * The Class JobControllerConfig.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 12:06 AM
 */
public class JobControllerConfig implements Config {

    //Default 10s.
    private long ideTimeWhenEveryHandlerBusy = 10000;

    public long getIdeTimeWhenEveryHandlerBusy() {
        return ideTimeWhenEveryHandlerBusy;
    }

    public void setIdeTimeWhenEveryHandlerBusy(long ideTimeWhenEveryHandlerBusy) {
        this.ideTimeWhenEveryHandlerBusy = ideTimeWhenEveryHandlerBusy;
    }
}
