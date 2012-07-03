package org.linkerz.crawler.core.queue;

import org.linkerz.crawler.core.job.Job;

import java.io.Serializable;

/**
 * The Class Queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:16 AM
 */
public interface Queue<J extends Job> extends Serializable {

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
