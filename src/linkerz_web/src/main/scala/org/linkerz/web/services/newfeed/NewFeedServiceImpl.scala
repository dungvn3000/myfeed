/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{UserClick, Link, NewFeed}
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import org.springframework.data.mongodb.core.query.{Order, Criteria, Query}
import collection.mutable.ListBuffer
import org.linkerz.weka.mongodb.JavaBeanBuilder
import weka.classifiers.trees.J48
import weka.filters.unsupervised.attribute.StringToWordVector
import weka.filters.Filter

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
    val userClicks = mongoOperations.findAll(classOf[UserClick])
    val userLikes = new ListBuffer[UserLike]
    userClicks.foreach(userClick => {
      val link = mongoOperations.findById(userClick.linkId, classOf[Link])
      val userLike = new UserLike
      userLike.title = link.title
      userLike.like = "no"
      if (userClick.clicked) {
        userLike.like = "yes"
      }
      userLikes += userLike
    })

    val train = JavaBeanBuilder.build(classOf[UserLike], userLikes.toList)

    val filter = new StringToWordVector
    filter.setInputFormat(train)

    val dataFiltered = Filter.useFilter(train, filter)
    dataFiltered.setClassIndex(0)

    val cls = new J48()
    cls.buildClassifier(dataFiltered)

    val query = Query.query(Criteria.where("title").exists(true)).limit(50)
    query.sort().on("indexDate", Order.DESCENDING)
    var links = mongoOperations.find(query, classOf[Link])
    links = links.filter(link => StringUtils.isNotBlank(link.title) && StringUtils.isNotBlank(link.description))

    val recommendLinks = new ListBuffer[Link]
    links.foreach(link => {
      val userLike = new UserLike
      userLike.title = link.title

      val test = JavaBeanBuilder.build(classOf[UserLike], List(userLike))
      filter.setInputFormat(test)

      val dataTestFiltered = Filter.useFilter(test, filter)
      dataTestFiltered.setClassIndex(0)

      val instance = dataTestFiltered.instance(0)
      val pred = cls.classifyInstance(instance)
      val like = instance.classAttribute().value(pred.toInt)

      if (like == "yes") {
        recommendLinks += link
      }
    })

    recommendLinks
  }
}
