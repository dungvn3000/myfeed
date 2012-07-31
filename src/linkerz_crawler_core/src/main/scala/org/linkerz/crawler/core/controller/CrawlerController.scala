/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller

import org.linkerz.job.queue.controller.BaseController
import com.hazelcast.core.{ItemEvent, ItemListener}

/**
 * The Class CrawlerController.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:52 AM
 *
 */

class CrawlerController extends BaseController with ItemListener[Any] {
  def itemAdded(item: ItemEvent[Any]) {
    println(item.getItem)
  }

  def itemRemoved(item: ItemEvent[Any]) {}
}
