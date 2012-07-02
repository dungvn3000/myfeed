package org.linkerz.crawler.core.queue;

import org.linkerz.crawler.core.job.Job;

/**
 * The Class Queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:16 AM
 */
public interface Queue<J extends Job> {

    /**
     * Add a job to the queue.
     * @param job
     */
    void add(J job);

    /**
     * Run the queue.
     */
    void run();

}
