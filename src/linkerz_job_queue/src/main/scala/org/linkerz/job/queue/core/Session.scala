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

trait Session {

  /**
   * Open the session.
   */
  def openSession(): Session

  /**
   * End the session.
   */
  def endSession()

}