/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.db

import org.linkerz.crawler.core.model.WebPage
import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import java.util
import org.linkerz.mongodb.model.Link

/**
 * The Class DBServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 9:28 PM
 *
 */

class DBServiceImpl extends DBService {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def save(webPages: List[WebPage]) {
    assert(webPages != null)
    if (!webPages.isEmpty) {
      val links = new util.ArrayList[Link](webPages.size)
      webPages.foreach(webPage => links.add(webPage.asLink()))
      mongoOperations.save(links)
    }
  }
}
