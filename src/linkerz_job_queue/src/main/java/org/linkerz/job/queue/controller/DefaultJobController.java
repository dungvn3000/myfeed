/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller;

import com.hazelcast.core.HazelcastInstance;
import org.linkerz.core.callback.CallBack;
import org.linkerz.core.config.Configurable;
import org.linkerz.job.queue.controller.config.JobControllerConfig;
import org.linkerz.job.queue.handler.Handler;
import org.linkerz.job.queue.job.Job;
import org.linkerz.job.queue.queue.JobQueue;
import org.linkerz.job.queue.session.RunInSession;
import org.linkerz.job.queue.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 2:18 PM
 */
public class DefaultJobController implements JobController, Configurable<JobControllerConfig> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJobController.class);
    private List<Handler> handlers;
    private HazelcastInstance instance;
    private JobControllerConfig config;
    private JobQueue<Job> jobQueue;

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
        Queue<Job> realQueue = instance.getQueue(Job.JOB_QUEUE);
        jobQueue = new JobQueue<Job>(realQueue);
        workerThread.start();
    }

    @SuppressWarnings({"ConstantConditions", "InfiniteLoopStatement"})
    private void doWorkLoop() {
        while (true) {
            synchronized (syncRoot) {
                try {
                    boolean done = false;
                    Job job = jobQueue.next();
                    if (job != null) {
                        for (Handler handler : handlers) {
                            if (handler.isFor(job.getClass())) {
                                CallBack callBack = job.getCallBack();
                                try {
                                    Session session = null;
                                    RunInSession runInSession = handler.getClass().getAnnotation(RunInSession.class);
                                    if (runInSession != null) {
                                        Class<? extends Session> sessionClass = runInSession.sessionClass();
                                        session = sessionClass.newInstance();
                                        session.start(job);
                                    }

                                    handler.handle(job, session);

                                    if (session != null) {
                                        session.end();
                                    }

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
                                } finally {
                                    //Mark the job was done. Even there is a exception.
                                    done = true;
                                }
                            }
                        }

                        //If the job hasn't done yet, re add it.
                        if (!done) {
                            boolean added = false;
                            while (!added) {
                                //may be all handler is busy
                                // Sleep 10S
                                logger.info("Sleep " + config.getIdeTimeWhenEveryHandlerBusy()
                                        + " ms because every handler is busy now");
                                Thread.sleep(config.getIdeTimeWhenEveryHandlerBusy());
                                added = jobQueue.add(job);
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

    @Override
    public void setConfig(JobControllerConfig config) {
        this.config = config;
    }

    @Override
    public JobControllerConfig getConfig() {
        return config;
    }
}
