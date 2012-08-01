/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job;

/**
 * The Class DistributeCrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/31/12, 2:59 PM
 */
public class DistributedCrawlJob implements DistributedJob {

    private String url;

    //For Serializable only
    public DistributedCrawlJob() {
    }

    public DistributedCrawlJob(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
