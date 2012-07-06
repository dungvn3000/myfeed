/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.queue;

import org.linkerz.job.queue.job.Job;

/**
 * The Class JobQueue.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 1:03 AM
 */
public class JobQueue<J extends Job> implements Queue<J> {

    private final java.util.Queue<J> realQueue;
    private int maxSize = -1;

    public JobQueue(java.util.Queue<J> realQueue) {
        this.realQueue = realQueue;
    }

    @Override
    public boolean add(J job) {
        if (maxSize == -1 || realQueue.size() < maxSize) {
            return realQueue.offer(job);
        }
        return false;
    }

    @Override
    public J next() {
        synchronized (realQueue) {
            return realQueue.poll();
        }
    }

    @Override
    public int size() {
        return realQueue.size();
    }

    @Override
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
