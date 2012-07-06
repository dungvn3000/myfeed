/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job.config;

import org.linkerz.core.config.Config;

/**
 * The Class CrawlJobConfigurable.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 6:41 PM
 */
public class CrawlJobConfig implements Config {

    //Maximum job number for the crawler.
    private int maxPageFetchForEachJob = -1;


    public int getMaxPageFetchForEachJob() {
        return maxPageFetchForEachJob;
    }

    public void setMaxPageFetchForEachJob(int maxPageFetchForEachJob) {
        this.maxPageFetchForEachJob = maxPageFetchForEachJob;
    }
}
