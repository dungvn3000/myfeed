/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller.config;

import org.linkerz.core.config.Config;

/**
 * The Class CrawlControllerConfig.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 12:13 AM
 */
public class CrawlControllerConfig implements Config {

    private int preferLocalJobNumber = 1000;
    private int numberOfCrawler = 5;
    //Maximum job number for the crawler.
    private int maxPageFetchForEachJob = -1;

    public int getPreferLocalJobNumber() {
        return preferLocalJobNumber;
    }

    public void setPreferLocalJobNumber(int preferLocalJobNumber) {
        this.preferLocalJobNumber = preferLocalJobNumber;
    }

    public int getNumberOfCrawler() {
        return numberOfCrawler;
    }

    public void setNumberOfCrawler(int numberOfCrawler) {
        this.numberOfCrawler = numberOfCrawler;
    }

    public int getMaxPageFetchForEachJob() {
        return maxPageFetchForEachJob;
    }

    public void setMaxPageFetchForEachJob(int maxPageFetchForEachJob) {
        this.maxPageFetchForEachJob = maxPageFetchForEachJob;
    }
}