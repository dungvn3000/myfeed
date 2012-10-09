/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core.{Job, Handler}
import org.linkerz.job.queue.job.SumJob

/**
 * The Class SyncHanlder.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 4:41 AM
 *
 */
class SyncHandler extends Handler[SumJob] {

  def accept(job: Job) = job.isInstanceOf[SumJob]

  protected def doHandle(job: SumJob) {
    job.result = job.x + job.y
  }

}
