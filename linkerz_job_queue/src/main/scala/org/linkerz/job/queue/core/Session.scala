/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.core

/**
 * The Class Session.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:39 PM
 *
 */

trait Session[J <: Job] extends Serializable {

  var job: J = _

  var currentDepth = 0

  //Counting child jobs was done by the current job.
  var subJobCount = 0

  //Starting count the time on current job.
  val startTime = System.currentTimeMillis

  /**
   * Time for done the job.
   * @return
   */
  def jobTime = System.currentTimeMillis - startTime

  /**
   * Open the session.
   */
  def openSession(job: J): Session[J]

  /**
   * End the session.
   */
  def endSession() {
    //Template method.
  }

}