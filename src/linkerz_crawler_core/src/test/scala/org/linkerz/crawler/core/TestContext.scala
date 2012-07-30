/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.junit.Ignore

/**
 * The Class TestContext.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:04 AM
 *
 */

@ContextConfiguration(locations = Array("/context.xml"))
@RunWith(classOf[SpringJUnit4ClassRunner])
@Ignore
class TestContext extends FunSuite with ApplicationContextAware {

  var context: ApplicationContext = _

  test("testContext") {
    assert(context != null)
  }

  def setApplicationContext(applicationContext: ApplicationContext) {
    context = applicationContext
  }
}
