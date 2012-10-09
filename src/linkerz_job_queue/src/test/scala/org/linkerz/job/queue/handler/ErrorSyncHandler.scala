/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core.{Handler, Job}
import org.linkerz.job.queue.exception.TestException
import org.linkerz.job.queue.job.EchoJob

/**
 * The Handler will throw exception when handle a job.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 5:44 AM
 *
 */
class ErrorSyncHandler extends Handler[EchoJob] {

  def accept(job: Job) = job.isInstanceOf[EchoJob]

  protected def doHandle(job: EchoJob) {
    throw new TestException
  }
}
