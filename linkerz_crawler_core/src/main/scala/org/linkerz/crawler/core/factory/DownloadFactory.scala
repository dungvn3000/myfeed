/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.factory

import org.linkerz.crawler.core.downloader.Downloader

/**
 * The Class DownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:37 PM
 *
 */

trait DownloadFactory {

  def createDownloader(): Downloader

  def createImageDownloader(): Downloader
}
