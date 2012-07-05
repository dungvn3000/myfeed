/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.queue;

import org.linkerz.core.job.Job;

/**
 * The Class JobQueue.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 1:03 AM
 */
public class JobQueue<J extends Job> implements Queue<J> {

    private java.util.Queue<J> realQueue;
    private boolean finished = false;

    public JobQueue(java.util.Queue<J> realQueue) {
        this.realQueue = realQueue;
    }

    @Override
    public boolean add(J job) {
        return realQueue.offer(job);
    }

    @Override
    public J getNext() {
        return realQueue.poll();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public int size() {
        return realQueue.size();
    }
}
