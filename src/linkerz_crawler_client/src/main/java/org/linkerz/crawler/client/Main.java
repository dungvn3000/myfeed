/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.client;

import org.linkerz.crawler.core.job.CrawlJob;

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:25 AM
 */
public class Main {

    public static void main(String[] args) {
        LinkerzCrawlerClient client = LinkerzCrawlerClient.connect("127.0.0.1", "dev", "dev");
        client.addJob(new CrawlJob("http://vnexpress.net/"));
        client.shutdown();
    }

}
