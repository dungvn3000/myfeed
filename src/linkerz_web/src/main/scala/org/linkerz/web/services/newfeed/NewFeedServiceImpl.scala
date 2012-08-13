/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{Link, NewFeed}
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import org.springframework.data.mongodb.core.query.{Order, Criteria, Query}

/**
 * The Class NewFeedServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 9:42 PM
 *
 */

class NewFeedServiceImpl extends NewFeedService {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def feedList = {
    mongoOperations.findAll(classOf[NewFeed])
  }

  def linkList = {
    val query = Query.query(Criteria.where("title").exists(true)).limit(20)
    query.sort().on("indexDate", Order.DESCENDING)
    val links = mongoOperations.find(query, classOf[Link])
    links.filter(link => StringUtils.isNotBlank(link.title) && link.title != "Document Moved")
  }
}
