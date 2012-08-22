/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.session

import org.linkerz.job.queue.core.Session
import org.linkerz.job.queue.job.SumJob
import grizzled.slf4j.Logging

/**
 * The Class SimpleSession.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 6:30 AM
 *
 */
class SimpleSession extends Session[SumJob] with Logging {

  def openSession(job: SumJob) = {
    this.job = job
    info("Opening Session")
    this
  }


  override def endSession() {
    info("Ending Session")
  }
}
