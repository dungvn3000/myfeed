package org.linkerz.crawler.core.controller;

import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.parser.Parser;
import org.linkerz.crawler.core.queue.Queue;

import java.io.Serializable;
import java.util.Map;

/**
 * The Class Controller.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:22 AM
 */
public interface Controller<Q extends Queue> extends Serializable {

    /**
     * Start crawl form this link.
     *
     * @param webLink the link of a web site.
     */
    void start(WebLink webLink);

    /**
     * Set map of downloaders for controller.
     *
     * @param downloaders for each download will be used for correct website.
     */
    void setDownloaders(Map<String, Downloader> downloaders);

    /**
     * Set map of parsers for controller.
     *
     * @param parsers for each parser will be used for correct website.
     */
    void setParsers(Map<String, Parser> parsers);

    /**
     * Set queue for controller.
     *
     * @param queue
     */
    void setQueue(Q queue);
}
