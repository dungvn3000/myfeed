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
import org.linkerz.weka.mongodb.JavaBeanBuilder
import weka.classifiers.trees.J48
import weka.filters.unsupervised.attribute.StringToWordVector
import weka.filters.Filter
import collection.mutable.ListBuffer
import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.meta.Vote

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
    val query = Query.query(Criteria.where("title").exists(true)).limit(50)
    query.sort().on("indexDate", Order.DESCENDING)
    val links = mongoOperations.find(query, classOf[Link])
    links.filter(link => StringUtils.isNotBlank(link.title) && StringUtils.isNotBlank(link.description))
  }

}
