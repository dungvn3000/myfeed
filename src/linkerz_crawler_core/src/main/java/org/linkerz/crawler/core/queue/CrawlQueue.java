package org.linkerz.crawler.core.queue;

import org.linkerz.crawler.core.downloader.Downloader;
import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.crawler.core.job.Job;
import org.linkerz.crawler.core.parser.Parser;

import java.util.LinkedList;
import java.util.Map;

/**
 * The Class CrawlQueue.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:17 AM
 */
public class CrawlQueue implements Queue<CrawlJob> {

    private Map<String, Downloader> downloaders;
    private Map<String, Parser> parsers;
    private java.util.Queue<CrawlJob> jobs = new LinkedList<CrawlJob>();

    @Override
    public void add(CrawlJob job) {
        job.setDownloaders(downloaders);
        job.setParsers(parsers);
        jobs.add(job);
    }

    @Override
    public void run() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Job job  = jobs.remove();
                    job.execute();
                }
            }
        };
        thread.run();
    }

    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    public void setParsers(Map<String, Parser> parsers) {
        this.parsers = parsers;
    }
}
