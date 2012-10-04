/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.newfeed

import org.linkerz.test.spring.SpringContext
import org.junit.Test
import collection.JavaConversions._

/**
 * The Class TestNewFeedService.
 *
 * @author Nguyen Duc Dung
 * @since 9/25/12 3:22 AM
 *
 */
class TestNewFeedService extends SpringContext {

  @Test
  def testRecommend() {
    val newFeedService = context.getBean(classOf[NewFeedService])
  }

}
