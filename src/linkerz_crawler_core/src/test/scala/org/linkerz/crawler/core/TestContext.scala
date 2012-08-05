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
import org.springframework.context.support.GenericXmlApplicationContext
import spring.SpringContext

/**
 * The Class TestContext.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:04 AM
 *
 */
class TestContext extends FunSuite with SpringContext {

  test("testContext") {
    assert(context != null)
  }

}
