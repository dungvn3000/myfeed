/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.controller.DownloaderController;
import org.linkerz.crawler.core.parser.controller.ParserController;
import org.linkerz.job.queue.job.Job;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractCrawlController<J extends Job> implements CrawlController<J> {

    protected DownloaderController downloaderController;
    protected ParserController parserController;

    @Override
    public void setDownloaderController(DownloaderController downloaderController) {
        this.downloaderController = downloaderController;
    }

    @Override
    public void setParserController(ParserController parserController) {
        this.parserController = parserController;
    }

}
