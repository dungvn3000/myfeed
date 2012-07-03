package org.linkerz.crawler.core.queue;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.job.Job;
import org.linkerz.crawler.core.parser.Parser;

import java.util.Map;

/**
 * The Class CrawlQueue.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:17 AM
 */
public class CrawlQueue implements Queue<CrawlJob>, ItemListener<CrawlJob> {

    private Map<String, Downloader> downloaders;
    private Map<String, Parser> parsers;
    private HazelcastInstance instance;
    private boolean done = false;

    @Override
    public void add(CrawlJob job) {
        job.setDownloaders(downloaders);
        job.setParsers(parsers);
        getJobQueue().add(job);
        getJobQueue().addItemListener(this, true);
    }

    @Override
    public void run() {
        while (!getJobQueue().isEmpty()) {
            Job job = getJobQueue().remove();
            if (job != null) {
                job.execute();
            }
        }
        done = true;
    }

    @Override
    public void itemAdded(ItemEvent<CrawlJob> item) {
        if (done) {
            done = false;
            run();
        }
    }

    @Override
    public void itemRemoved(ItemEvent<CrawlJob> item) {
    }

    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }

    public void setInstance(HazelcastInstance instance) {
        this.instance = instance;
    }

    public IQueue<CrawlJob> getJobQueue() {
        return instance.getQueue("jobQueue");
    }
}
