/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import org.linkerz.core.callback.JobCallBack;
import org.linkerz.core.handler.Handler;
import org.linkerz.core.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 2:18 PM
 */
public class DefaultJobController implements JobController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJobController.class);
    private List<Handler> handlers;
    private HazelcastInstance instance;

    private final Thread workerThread;

    private final Object syncRoot = new Object();

    public DefaultJobController() {
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWorkLoop();
            }
        });
        workerThread.setDaemon(true);
    }

    @Override
    public void start() {
        workerThread.start();
    }

    @SuppressWarnings("ConstantConditions")
    private void doWorkLoop() {
        while (true) {
            synchronized (syncRoot) {
                try {
                    Job job = getJobQueue().poll();
                    if (job != null) {
                        for (Handler handler : handlers) {
                            if (handler.isFor(job.getClass())) {
                                JobCallBack callBack = job.getCallBack();
                                try {
                                    handler.handle(job);

                                    if (callBack != null) {
                                        callBack.onSuccess(job.getResult());
                                    }
                                    //Make sure only one handler handle for each kind of job at
                                    //the same time.
                                    break;
                                } catch (Exception e) {
                                    if (callBack != null) {
                                        callBack.onFailed(e);
                                    }
                                    logger.error(e.getMessage(), e);
                                }
                            }
                        }
                    }
                    // Yielding context to another thread
                    Thread.sleep(1);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.instance = hazelcastInstance;
    }

    private IQueue<Job> getJobQueue() {
        return instance.getQueue(Job.JOB_QUEUE);
    }
}
