/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import org.linkerz.core.job.Job;
import org.linkerz.crawler.core.downloader.controller.DownloaderController;
import org.linkerz.crawler.core.parser.controller.ParserController;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractCrawlController<J extends Job> implements CrawlController<J> {

    protected DownloaderController downloaderController;
    protected ParserController parserController;
    protected HazelcastInstance instance;

    @Override
    public void setDownloaderController(DownloaderController downloaderController) {
        this.downloaderController = downloaderController;
    }

    @Override
    public void setParserController(ParserController parserController) {
        this.parserController = parserController;
    }

    @Override
    public IQueue<J> getQueue() {
        return instance.getQueue(Job.JOB_QUEUE);
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.instance = hazelcastInstance;
    }
}
