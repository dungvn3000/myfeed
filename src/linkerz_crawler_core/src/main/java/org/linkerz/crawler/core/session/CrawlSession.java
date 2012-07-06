/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.session;

import org.linkerz.crawler.core.job.CrawlJob;
import org.linkerz.job.queue.queue.JobQueue;
import org.linkerz.job.queue.queue.Queue;
import org.linkerz.job.queue.session.Session;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 4:00 PM
 */
public class CrawlSession implements Session<CrawlJob> {

    private Queue<CrawlJob> localJobQueue;
    private List<Thread> threads;
    private CrawlJob crawlJob;
    private int count = 0;

    @Override
    public void start(CrawlJob job) {
        localJobQueue = new JobQueue<CrawlJob>(new LinkedList<CrawlJob>());
        localJobQueue.setMaxSize(job.getConfig().getMaxPageFetchForEachJob());
        threads = new ArrayList<Thread>();
        count = 0;
        this.crawlJob = job;
        localJobQueue.add(job);
    }

    @Override
    public void end() {
        threads.clear();
    }

    public void count() {
        count += 1;
    }

    public Queue<CrawlJob> getLocalJobQueue() {
        return localJobQueue;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public int getCount() {
        return count;
    }

    @Override
    public CrawlJob getJob() {
        return crawlJob;
    }
}
