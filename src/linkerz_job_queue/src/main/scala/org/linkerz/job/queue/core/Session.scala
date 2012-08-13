/*
 * Copyright (C) 2012 - 2013 LinkerZ
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

  /**
   * Open the session.
   */
  def openSession(job: J): Session[J]

  /**
   * End the session.
   */
  def endSession() {}

}