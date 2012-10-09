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

trait Session[J <: Job] {

  var job: J = _

  var currentDepth = 0

  //Counting child jobs was done by the current job.
  var subJobCount = 0

  //Starting time on current job.
  var startTime = System.currentTimeMillis

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
  def endSession() {}

}