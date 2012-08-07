/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext

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
