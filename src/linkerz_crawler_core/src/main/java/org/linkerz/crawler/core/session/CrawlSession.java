/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.session;

import org.linkerz.core.queue.JobQueue;
import org.linkerz.core.queue.Queue;
import org.linkerz.core.session.Session;
import org.linkerz.crawler.core.job.CrawlJob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 4:00 PM
 */
public class CrawlSession implements Session {

    private Queue<CrawlJob> localJobQueue;
    private List<Thread> threads;
    private int count = 0;

    @Override
    public void start() {
        localJobQueue = new JobQueue<CrawlJob>(new LinkedList<CrawlJob>());
        threads = new ArrayList<Thread>();
        count = 0;
    }

    @Override
    public void end() {
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
}
