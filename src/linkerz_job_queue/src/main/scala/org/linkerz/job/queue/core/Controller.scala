/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class Controller.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:38 PM
 *
 */

trait Controller {

  /**
   * Start the controller
   */
  def start()

  /**
   * Stop the controller.
   */
  def stop()

  /**
   * Add a job to the queue
   * @param job
   */
  def add(job: Job)
}
