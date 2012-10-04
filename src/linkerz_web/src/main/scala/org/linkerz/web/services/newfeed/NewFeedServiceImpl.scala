/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{UserRead, Link, NewFeed}
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
    val query = Query.query(Criteria.where("title").exists(true))
    val userReads = mongoOperations.findAll(classOf[UserRead])
    var links = mongoOperations.find(query, classOf[Link])
    links = links.filter(link => {
      StringUtils.isNotBlank(link.title) &&
        StringUtils.isNotBlank(link.description) &&
        userReads.find(userRead => userRead.linkId == link.id).isEmpty
    })

    links.sortWith((link1 , link2) => link1.indexDate.before(link2.indexDate))

    if (links.size() > 20) {
      links = links.subList(0, 20)
    }

    links.foreach(link => {
      val userRead = new UserRead
      userRead.linkId = link.id
      userRead.userName = "dungvn3000"
      mongoOperations.save(userRead)
    })

    links
  }

}
