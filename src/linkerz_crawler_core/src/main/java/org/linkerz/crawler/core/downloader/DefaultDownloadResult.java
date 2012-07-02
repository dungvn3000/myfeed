package org.linkerz.crawler.core.downloader;

import org.linkerz.crawler.core.model.WebPage;

/**
 * The Class DefaultDownloadResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 6:25 PM
 */
public class DefaultDownloadResult implements DownloadResult {

    private WebPage webPage;

    public DefaultDownloadResult() {
    }

    public DefaultDownloadResult(WebPage webPage) {
        this.webPage = webPage;
    }

    public WebPage getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }
}
