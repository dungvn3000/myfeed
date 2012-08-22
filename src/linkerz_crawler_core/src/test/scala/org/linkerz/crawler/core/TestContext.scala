/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestContext.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:04 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestContext extends FunSuite with SpringContext {

  test("testContext") {
    assert(context != null)
  }

}
