/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.controller

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext

/**
 * The Class TestNewFeedController.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:04 AM
 *
 */

class TestNewFeedController extends FunSuite with SpringContext {

  test("testController") {
    val controller = context.getBean("newFeedController", classOf[NewFeedController])
    controller.start()
//    controller.stop()

    Thread.sleep(10005)
  }

}
