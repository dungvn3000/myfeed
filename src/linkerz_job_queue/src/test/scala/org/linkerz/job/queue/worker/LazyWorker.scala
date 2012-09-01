/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.job.queue.job.{EmptyJob, SumJob}
import org.linkerz.job.queue.session.SimpleSession

/**
 * The Class LazyWorker.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:36 AM
 * 
 */
case class LazyWorker(id: Int) extends Worker[EmptyJob, SimpleSession] {
  def analyze(job: EmptyJob, session: SimpleSession) {
    info("Worker " + id + " is working")
    //Sleep first, and done nothing.
    Thread.sleep(1000)
  }
}
