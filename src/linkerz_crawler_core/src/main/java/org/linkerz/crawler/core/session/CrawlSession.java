/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.session;

import org.linkerz.crawler.core.crawler.Crawler;
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
    private List<Crawler> crawlers;
    private CrawlJob crawlJob;
    private int jobCount = 0;
    private int errorCount = 0;
    private boolean finished = false;

    @Override
    public void start(CrawlJob job) {
        localJobQueue = new JobQueue<CrawlJob>(new LinkedList<CrawlJob>());
        crawlers = new ArrayList<Crawler>();
        this.crawlJob = job;
        localJobQueue.add(job);
    }

    @Override
    public void end() {

    }

    public void countJob() {
        jobCount += 1;
    }

    public void countError() {
        errorCount += 1;
    }

    public Queue<CrawlJob> getLocalJobQueue() {
        return localJobQueue;
    }

    public List<Crawler> getCrawlers() {
        return crawlers;
    }

    public int getJobCount() {
        return jobCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    @Override
    public CrawlJob getJob() {
        return crawlJob;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
