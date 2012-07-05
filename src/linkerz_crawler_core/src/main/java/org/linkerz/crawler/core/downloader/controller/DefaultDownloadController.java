/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader.controller;

import org.linkerz.crawler.core.downloader.Downloader;

import java.util.Map;

/**
 * The Class DefaultDownloadController.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 4:22 PM
 */
public class DefaultDownloadController implements DownloadController {

    protected Map<String, Downloader> downloaders;

    @Override
    public void setDownloaders(Map<String, Downloader> downloaders) {
        this.downloaders = downloaders;
    }

    @Override
    public Downloader get(String url) {
        return downloaders.get("*");
    }
}
