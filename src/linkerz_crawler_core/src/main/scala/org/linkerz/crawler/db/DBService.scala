/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.db

import org.linkerz.mongodb.model.Link

/**
 * The Class DBService.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 7:42 PM
 *
 */

trait DBService {

  /**
   * Save a link to database
   * @param link
   * @return
   */
  def save(link: Link) : Link

}
