/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.core.job;

import org.linkerz.core.callback.CallBack;

/**
 * The Class AbstractJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/4/12, 1:57 PM
 */
public abstract class AbstractJob<R> implements Job<R> {

    protected CallBack<R> callBack;
    protected R result;

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
}
