/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.controller

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestNewFeedController.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:04 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestNewFeedController extends FunSuite with SpringContext {

  test("testController") {
    val controller = context.getBean("newFeedController", classOf[NewFeedController])
    controller.start()
    Thread.sleep(1000 * 60 * 2)
    controller.stop()
  }

}
