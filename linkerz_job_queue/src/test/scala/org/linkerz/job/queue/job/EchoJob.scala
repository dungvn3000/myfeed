/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.job

import org.linkerz.job.queue.core.Job

/**
 * The Class EchoJob.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 1:04 PM
 *
 */
case class EchoJob(msg: String) extends Job {
  def result = Some(msg)
}
