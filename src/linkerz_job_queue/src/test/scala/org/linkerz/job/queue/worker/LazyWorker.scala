/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.job.queue.job.SumJob
import org.linkerz.job.queue.session.SimpleSession

/**
 * The Class LazyWorker.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:36 AM
 * 
 */
case class LazyWorker(id: Int) extends Worker[SumJob, SimpleSession] {
  def analyze(job: SumJob, session: SimpleSession) {
    info("Worker " + id + " is working")
    //Sleep first, before done the job.
    Thread.sleep(1000)
    job.result = job.x + job.y
  }
}
