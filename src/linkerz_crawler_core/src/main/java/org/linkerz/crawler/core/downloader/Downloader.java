/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader;

import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.model.WebLink;

import java.io.Serializable;

/**
 * The Class Downloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 12:25 AM
 */
public interface Downloader<R extends DownloadResult> extends Serializable {
    public DownloadResult download(WebLink webLink) throws Exception;
}
