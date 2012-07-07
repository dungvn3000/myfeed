/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import collection.mutable

/**
 * The Class Queue.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:20 PM
 *
 */

/**
 * A queue contain list of the job need todo.
 */
trait Queue {

  /**
   * Add a job to a queue
   * @param job
   * @return false if it was not added.
   */
  def add(job: Job): Boolean

  /**
   * Take and remove the next job from the queue
   * @return
   */
  def next(): Option[Job]
}

/**
 * The basic job queue.
 */
class JobQueue extends Queue {

  val realQueue = new mutable.Queue[Job]()

  /**
   * Add a job to a queue
   * @param job
   * @return false if it was not added.
   */
  def add(job: Job) = {
    (realQueue += job) != null
  }

  /**
   * Take and remove the next job from the queue
   * @return
   */
  def next(): Option[Job] = {
    if (realQueue.size > 0) return Some(realQueue.dequeue())
    None
  }
}
