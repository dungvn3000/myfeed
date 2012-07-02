package org.linkerz.crawler.core.downloader;

import org.linkerz.crawler.core.model.WebLink;
import org.linkerz.crawler.core.model.WebPage;

/**
 * The Class DefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 2:14 AM
 */
public class DefaultDownloader extends AbstractDownloader implements Downloader {
    @Override
    public WebPage download(WebLink webLink) {
        System.out.println(webLink.getUrl());
        return new WebPage();
    }
}
