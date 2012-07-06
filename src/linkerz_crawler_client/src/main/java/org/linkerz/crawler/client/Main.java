/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.client;

import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.job.config.CrawlJobConfig;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:25 AM
 */
public class Main {

    public static void main(String[] args) {
        LinkerzCrawlerClient client = LinkerzCrawlerClient.connect("127.0.0.1", "dev", "dev");
        CrawlJobConfig config = new CrawlJobConfig();
        config.setMaxPageFetchForEachJob(10);
        CrawlJob crawlJob = new CrawlJob("http://vnexpress.net/", config);
        client.addJob(crawlJob);
        client.shutdown();
    }

}
