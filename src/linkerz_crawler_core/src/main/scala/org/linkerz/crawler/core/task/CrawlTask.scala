/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.task


/**
 * The Class CrawlTask.
 *
 * @author Nguyen Duc Dung
 * @since 7/30/12, 11:23 PM
 *
 */

/**
 * Same with job class but it's using for distribute calculate.
 */
class CrawlTask(id: String) extends Runnable with Serializable {
  def run() {
    println("begin " + id)
    Thread.sleep(10000)
    println("Hello")
  }

  def call() = {
    println("dung ne")
    "dung ne"
  }
}
