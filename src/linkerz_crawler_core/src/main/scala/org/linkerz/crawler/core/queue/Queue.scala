package org.linkerz.crawler.core.queue

import org.linkerz.crawler.core.job.Job

/**
 * The Class Queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:37 PM
 *
 */

trait Queue {

  /**
   * Add a job to the queue.
   * @param job
   */
  def add(job: Job)


  /**
   * Run the queue.
   */
  def run()

}
