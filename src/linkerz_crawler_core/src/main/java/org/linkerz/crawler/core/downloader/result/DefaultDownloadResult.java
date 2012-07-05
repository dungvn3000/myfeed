/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader.result;

import org.linkerz.crawler.core.model.WebPage;

/**
 * The Class DefaultDownloadResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 6:25 PM
 */
public class DefaultDownloadResult implements DownloadResult {

    private int httpStatus;
    private WebPage webPage;

    public DefaultDownloadResult() {
    }

    public DefaultDownloadResult(WebPage webPage) {
        this.webPage = webPage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public WebPage getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }
}
