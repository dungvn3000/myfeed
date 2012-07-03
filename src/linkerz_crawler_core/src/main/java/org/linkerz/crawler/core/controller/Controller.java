package org.linkerz.crawler.core.controller;

import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IQueue;
import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.job.Job;
import org.linkerz.crawler.core.parser.Parser;

import java.io.Serializable;
import java.util.Map;

/**
 * The Class Controller.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:22 AM
 */
public interface Controller<J extends Job> extends Serializable, HazelcastInstanceAware {


    /**
     * Start the controller to control crawling.
     */
    void start();

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
     * Get the queue.
     * @return
     */
    IQueue<J> getQueue();
}
