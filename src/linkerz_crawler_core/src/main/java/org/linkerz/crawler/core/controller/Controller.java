/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IQueue;
import org.linkerz.core.job.Job;
import org.linkerz.crawler.core.downloader.controller.DownloadController;
import org.linkerz.crawler.core.parser.controller.ParserController;

import java.io.Serializable;

/**
 * The Class Controller.
 *
 * The controller will handel jobs in the queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:22 AM
 */
public interface Controller<J extends Job> extends Serializable, HazelcastInstanceAware {

    /**
     * Set the download controller.
     * @param downloadController
     */
    void setDownloadController(DownloadController downloadController);


    /**
     * Set the parser controller.
     * @param parserController
     */
    void setParserController(ParserController parserController);

    /**
     * Get the queue.
     * @return
     */
    IQueue<J> getQueue();
}
