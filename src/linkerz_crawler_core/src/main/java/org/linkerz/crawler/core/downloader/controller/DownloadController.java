/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader.controller;

import org.linkerz.crawler.core.downloader.Downloader;

import java.util.Map;

/**
 * The Class DownloadController.
 *
 * @author Nguyen Duc Dung
 * @since 7/5/12, 4:08 PM
 */
public interface DownloadController {

    /**
     * Set map of downloaders for controller.
     *
     * @param downloaders for each download will be used for correct website.
     */
    void setDownloaders(Map<String, Downloader> downloaders);


    /**
     * Return a downloader for the url.
     * @param url
     * @return
     */
    Downloader get(String url);

}
