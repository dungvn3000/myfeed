/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class Job.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:21 PM
 *
 */

/**
 * The trait job represent for a job. It can be add to a Job Queue.
 */
trait Job {

  /**
   * Return the result of the job.
   * @return
   */
  def get(): Option[Any]

}

/**
 * Represent for a sub job.
 * That mean when you do a job then you realize there are many sub jobs for it.
 * And you create a new job. So that is what SubJob using for.
 * <br>
 * <b>Note:</b> The job and it's subJob will be done in same session.
 */
trait SubTob extends Job {

  /**
   * Job parent.
   */
  var parent: Job = _
}