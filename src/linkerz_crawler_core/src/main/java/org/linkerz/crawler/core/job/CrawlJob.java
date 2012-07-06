/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job;

import org.linkerz.core.job.AbstractJob;
import org.linkerz.crawler.core.job.config.CrawlJobConfig;
import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:12 AM
 */
public class CrawlJob extends AbstractJob<Void, CrawlJobConfig> {

    private WebLink webLink;
    private CrawlJobConfig config;

    public CrawlJob() {
        //Only for Serializable.
    }

    public CrawlJob(String url) {
        this(url, new CrawlJobConfig());
    }

    public CrawlJob(String url, CrawlJobConfig config) {
        this.webLink = new WebLink(url);
        this.config = config;
    }

    public CrawlJob(WebLink webLink, CrawlJobConfig config) {
        this.webLink = webLink;
        this.config = config;
    }

    public CrawlJob(WebLink webLink) {
        this(webLink, new CrawlJobConfig());
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }


    @Override
    public void setConfig(CrawlJobConfig config) {
        this.config = config;
    }

    @Override
    public CrawlJobConfig getConfig() {
        return config;
    }
}
