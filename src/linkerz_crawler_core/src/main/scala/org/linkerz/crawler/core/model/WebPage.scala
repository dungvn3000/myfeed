/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import org.linkerz.mongodb.model.Link

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

class WebPage {

  var webUrl: WebUrl = _
  var content: Array[Byte] = _

  /**
   * Convenient method to convert a webpage to link model to store the database.
   * @return
   */
  def asLink() = {
    val link = new Link
    link.url = webUrl.url
    link.content = content
    link
  }
}
