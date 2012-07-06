/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.job;

import org.linkerz.core.callback.CallBack;
import org.linkerz.core.config.JobConfig;

/**
 * The Class AbstractJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:57 PM
 */
public abstract class AbstractJob<R, C extends JobConfig> implements Job<R, C> {

    protected CallBack<R> callBack;
    protected R result;
    protected C config;

    @Override
    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public void setCallBack(CallBack<R> callBack) {
        this.callBack = callBack;
    }

    @Override
    public CallBack<R> getCallBack() {
        return callBack;
    }

    @Override
    public C getConfig() {
        return config;
    }

    @Override
    public void setConfig(C config) {
        this.config = config;
    }
}
