/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job;

import org.linkerz.core.job.AbstractJob;
import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:12 AM
 */
public class CrawlJob extends AbstractJob<Void> {

    private WebLink webLink;

    public CrawlJob() {
    }

    public CrawlJob(String url) {
        this.webLink = new WebLink(url);
    }

    public CrawlJob(WebLink webLink) {
        this.webLink = webLink;
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public void setWebLink(WebLink webLink) {
        this.webLink = webLink;
    }
}
