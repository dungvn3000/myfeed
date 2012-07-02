package org.linkerz.crawler.core.callback;

/**
 * The Class JobCallBack.
 *
 * @author Nguyen Duc Dung
 * @since 7/3/12, 4:48 AM
 */
public interface JobCallBack<R> {

    /**
     * Run when the job failed.
     * @param e
     * @param prams
     */
    void onFailed(Exception e, Object... prams);

    /**
     * Run when the job run success.
     * @param r
     */
    void onSuccess(R r);

}
