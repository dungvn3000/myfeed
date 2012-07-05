/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader;

import org.junit.Test;
import org.linkerz.crawler.core.downloader.result.DownloadResult;
import org.linkerz.crawler.core.model.WebLink;

/**
 * The Class TestDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 2:31 PM
 */
public class TestDownloader {

    @Test
    public void testDownloader() throws Exception {
        DefaultDownloader downloader = new DefaultDownloader();
        DownloadResult result = downloader.download(new WebLink("http://vnexpress.net/"));
    }

}
