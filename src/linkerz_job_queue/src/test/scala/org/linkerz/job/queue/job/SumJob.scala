/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.job

import org.linkerz.job.queue.core.Job

/**
 * The Class EchoJob.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 4:40 AM
 *
 */
case class SumJob(x: Int, y: Int) extends Job {

  private var _result: Option[Int] = None

  def result = _result

  def result_=(result: Int) {
    _result = Some(result)
  }

}
