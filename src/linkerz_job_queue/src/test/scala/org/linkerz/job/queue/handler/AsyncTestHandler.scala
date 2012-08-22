/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.job.SumJob
import org.linkerz.job.queue.session.SimpleSession
import org.linkerz.job.queue.core.Job
import org.linkerz.job.queue.worker.LazyWorker

/**
 * The Class AsyncTestHandler.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:29 AM
 *
 */
class AsyncTestHandler extends AsyncHandler[SumJob, SimpleSession] {

  protected def createWorker(numberOfWorker: Int) {
    for (i <- 0 to numberOfWorker - 1) {
      workers += LazyWorker(i)
    }
  }

  protected def createSubJobs(job: SumJob) {
    //Making 10 sub job for testing
    for (i <- 0 to 10) {
      subJobQueue += SumJob(1, 2)
    }
  }

  def sessionClass = classOf[SimpleSession]

  def accept(job: Job) = job.isInstanceOf[SumJob]
}
