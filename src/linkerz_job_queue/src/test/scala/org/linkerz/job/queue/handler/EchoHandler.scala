/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core.{Job, Handler}
import org.linkerz.job.queue.job.EchoJob
import grizzled.slf4j.Logging

/**
 * The Class EchoHandler.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 12:17 PM
 *
 */
class EchoHandler(name: String = "EchoHandler") extends Handler[EchoJob] {

  def accept(job: Job) = job.isInstanceOf[EchoJob]

  protected def doHandle(job: EchoJob) {
    println(name + " " + job.msg)
  }
}
