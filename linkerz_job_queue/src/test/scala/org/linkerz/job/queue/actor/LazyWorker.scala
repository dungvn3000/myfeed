/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.actor

import org.linkerz.job.queue.job.{EmptyJob, SumJob}
import org.linkerz.job.queue.session.SimpleSession

/**
 * The Class LazyWorker.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:36 AM
 * 
 */
class LazyWorker extends Worker[EmptyJob, SimpleSession] {
  def work(job: EmptyJob, session: SimpleSession) {
    info("LazyWorker is going to sleep")
    //Sleep first, and done nothing.
    Thread.sleep(1000)
  }
}
