package org.linkerz.crawler.core.downloader;

import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class Downloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:25 AM
 */
public interface Downloader {
    public void download(WebLink webLink);
}
