/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.server

import org.springframework.context.support.GenericXmlApplicationContext
import com.hazelcast.core.HazelcastInstance
import org.linkerz.crawler.bot.controller.NewFeedController

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/30/12, 8:59 PM
 *
 */

object Main extends App {

  val context = new GenericXmlApplicationContext("application.xml")
  val hazelcast = context.getBean("instance", classOf[HazelcastInstance])
  val controller = context.getBean("newFeedController", classOf[NewFeedController])
  controller.start()

}
