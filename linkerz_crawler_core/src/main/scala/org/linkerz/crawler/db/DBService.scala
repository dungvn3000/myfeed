/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.db

import org.linkerz.mongodb.model.Link
import org.linkerz.crawler.core.model.{WebUrl, WebPage}

/**
 * The Class DBService.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 7:42 PM
 *
 */

trait DBService {


  /**
   * Saving a web page and make a connection to his parent.
   * @param webPage
   */
  def save(webPage: WebPage)


  def find(webUrl: WebUrl): Link
}
