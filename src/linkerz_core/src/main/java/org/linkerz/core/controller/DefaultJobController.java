/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import org.linkerz.core.callback.JobCallBack;
import org.linkerz.core.handler.Handler;
import org.linkerz.core.job.Job;

import java.util.List;

/**
 * The Class DefaultController.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 2:18 PM
 */
public class DefaultJobController implements JobController, ItemListener<Job> {

    private List<Handler> handlers;
    private HazelcastInstance instance;
    private boolean done = false;

    @Override
    public void start() {
        getJobQueue().addItemListener(this, true);
        run();
    }

    private void run() {
        while (!getJobQueue().isEmpty()) {
            Job job = getJobQueue().remove();
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
                        e.printStackTrace();
                    }
                }
            }
        }
        done = true;
    }

    @Override
    public void itemAdded(ItemEvent<Job> item) {
        if (done) {
            done = false;
            run();
        }
    }

    @Override
    public void itemRemoved(ItemEvent<Job> item) {
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
