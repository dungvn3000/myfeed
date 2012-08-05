/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.db

import org.linkerz.mongodb.model.Link
import org.linkerz.crawler.core.model.WebPage

/**
 * The Class DBService.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 7:42 PM
 *
 */

trait DBService {

  /**
   * Save a list web pages to database
   * @param webPages
   * @return
   */
  def save(webPages: List[WebPage])

}
