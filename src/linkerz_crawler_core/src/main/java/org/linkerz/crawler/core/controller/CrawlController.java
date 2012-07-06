/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.controller.DownloaderController;
import org.linkerz.crawler.core.parser.controller.ParserController;
import org.linkerz.job.queue.job.Job;

import java.io.Serializable;

/**
 * The Class Controller.
 *
 * The controller will handel jobs in the queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:22 AM
 */
public interface CrawlController<J extends Job> extends Serializable {

    /**
     * Set the download controller.
     * @param downloaderController
     */
    void setDownloaderController(DownloaderController downloaderController);


    /**
     * Set the parser controller.
     * @param parserController
     */
    void setParserController(ParserController parserController);

}
