/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.crawler;

import org.linkerz.crawler.core.job.CrawlJob;

/**
 * The Class Crawler.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 8:17 PM
 */
public interface Crawler extends Runnable {

    /**
     * Checks if this crawler should do the task or not.
     * @param crawlJob
     * @return
     */
    boolean shouldCrawl(CrawlJob crawlJob);

}
