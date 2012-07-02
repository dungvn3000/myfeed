package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.queue.CrawlQueue;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:57 AM
 */
public class DefaultController extends AbstractController {

    private CrawlQueue queue;

    @Override
    public void start(WebLink webLink) {
        queue.setDownloaders(downloaders);
        queue.setParsers(parsers);
        queue.add(new CrawlJob(webLink));
        queue.run();
    }

    public void setQueue(CrawlQueue queue) {
        this.queue = queue;
    }
}
