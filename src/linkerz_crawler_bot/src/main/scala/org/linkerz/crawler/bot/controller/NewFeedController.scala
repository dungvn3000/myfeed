/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.controller

import org.linkerz.crawler.core.controller.CrawlerController
import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.NewFeed
import collection.JavaConversions._

/**
 * The Class NewFeedController.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 3:56 AM
 *
 */

class NewFeedController {

  @BeanProperty
  var crawlerController: CrawlerController = _

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def start() {
    var newFeeds = mongoOperations.findAll(classOf[NewFeed])
    newFeeds.foreach(f => println(f.name))
  }
}
