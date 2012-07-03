package org.linkerz.crawler.core.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.job.Job;
import org.linkerz.crawler.core.parser.Parser;

import java.util.Map;

/**
 * The Class AbstractController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:56 AM
 */
public abstract class AbstractController<J extends Job> implements Controller<J> {

    protected Map<String, Downloader> downloaders;
    protected Map<String, Parser> parsers;
    protected HazelcastInstance instance;

    @Override
    public IQueue<J> getQueue() {
        return instance.getQueue("jobQueue");
    }

    @Override
    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    @Override
    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.instance = hazelcastInstance;
    }
}
