/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.job

import java.util
import org.bson.types.ObjectId
import org.linkerz.parser.model.{Article, WebUrl}

/**
 * The Class CrawlJobResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

class CrawlJobResult extends Serializable {

  var webUrl: WebUrl = _
  //Using java list for better performance.
  var webUrls: java.util.List[WebUrl] = new util.ArrayList[WebUrl]()
  var content: Array[Byte] = Array.empty[Byte]

  //Meta data
  var contentType: String = _
  var contentEncoding: String = "UTF-8"

  var feedId: ObjectId = _

  var article: Option[Article] = None

  /**
   * Convenient method to convert a CrawlJobResult to link model to store the database.
   * @return
   */
  def asLink = {
    None
  }
}
