/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.job;

import org.linkerz.core.callback.JobCallBack;

import java.io.Serializable;

/**
 * The Class Job.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 3:11 AM
 */
public interface Job<R> extends Serializable {

    public static final String JOB_QUEUE = "jobQueue";

    /**
     * Get JobCallback.
     * @return
     */
    JobCallBack<R> getCallBack();

    /**
     * Return result of this job.
     * @return
     */
    R getResult();
}
