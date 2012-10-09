/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core

import org.linkerz.test.spring.SpringContext
import org.junit.Test

/**
 * The Class TestContext.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:04 AM
 *
 */
class TestContext extends SpringContext {

  @Test
  def testContext() {
    assert(context != null)
  }

}
