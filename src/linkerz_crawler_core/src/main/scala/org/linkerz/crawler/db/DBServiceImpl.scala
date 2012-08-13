/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.db

import org.linkerz.crawler.core.model.{WebUrl, WebPage}
import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{LinkConnection, Link}
import org.springframework.data.mongodb.core.query.Criteria._
import org.springframework.data.mongodb.core.query.Query._
import java.util

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

  def save(webPage: WebPage) {
    //Check the page is exist on the database or not.
    val result = find(webPage.webUrl)

    if (result == null) {
      val link = webPage.asLink()
      mongoOperations.save(link)

      if (webPage.parent != null) {
        //find the parent id in the database

        val parent = find(webPage.parent.webUrl)
        assert(parent != null, "Can not find the website " + webPage.parent.webUrl.url)

        val connection = new LinkConnection(parent.id, link.id)
        mongoOperations.save(connection)
      }
    } else {
      //If the web page already exist in the database, then update the content

      result.content = webPage.content
      result.indexDate = new util.Date
      mongoOperations.save(result)
    }
  }

  def find(webUrl: WebUrl) = {
    mongoOperations.findOne(query(where("url").is(webUrl.url)), classOf[Link])
  }
}
