/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import actors.Actor


/**
 * The Class Worker.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:53 AM
 *
 */

trait Worker[J <: Job] extends Actor {

  /**
   * The handler will check the worker is free or not, if he free, he has to work
   * @return
   */
  def isFree: Boolean

  /**
   * Work and analyze the job, if it has SubJobs then tell handler.
   * @param job
   * @param session
   * @return list of sub job.
   */
  def work(job: J, session: Session): List[J]

}
