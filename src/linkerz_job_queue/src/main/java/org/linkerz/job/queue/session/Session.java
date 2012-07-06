/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.session;

import org.linkerz.job.queue.job.Job;

/**
 * The Class Session.
 *
 * @author Nguyen Duc Dung
 * @since 7/6/12, 3:26 AM
 */
public interface Session<J extends Job> {

    /**
     * Start a session.
     */
    void start(J job);


    /**
     * Return current job.
     * @return
     */
    J getJob();

    /**
     * End a session.
     */
    void end();

}
