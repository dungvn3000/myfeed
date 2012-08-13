/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.controller

import org.linkerz.crawler.core.controller.CrawlerController
import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.NewFeed
import collection.JavaConversions._
import org.linkerz.crawler.core.job.CrawlJob

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
//    while (true) {
//      val newFeeds = mongoOperations.findAll(classOf[NewFeed])
//      newFeeds.foreach(newFeed => {
//        val job = new CrawlJob(newFeed.url)
//        crawlerController.add(job)
//      })
//      Thread.sleep(1000 * 60 * 30)
//    }
  }

  def test() {
    println("dung ne")
  }

  def stop() {
    crawlerController.stop()
  }

}
