/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.session

import org.linkerz.job.queue.core.Session
import org.linkerz.job.queue.job.EmptyJob
import grizzled.slf4j.Logging

/**
 * The Class SimpleSession.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:30 AM
 *
 */
class SimpleSession extends Session[EmptyJob] with Logging {

  def openSession(job: EmptyJob) = {
    this.job = job
    info("Opening Session")
    this
  }

  override def endSession() {
    info("Ending Session " + jobTime + " ms")
  }
}
