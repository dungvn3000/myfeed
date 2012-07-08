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
trait Queue[J <: Job] {

  /**
   * The real queue in side the queue
   * @return
   */
  def realQueue: mutable.Queue[J]

  /**
   * Add a job to a queue
   * @param job
   * @return false if it was not added.
   */
  def +=(job: J): Boolean = (realQueue += job) != null

  /**
   * Add a job list
   * @param jobs
   * @return
   */
  def ++=(jobs: List[J]) = realQueue ++= jobs

  /**
   * Clear all jobs.
   */
  def clear() {realQueue.clear()}

  /**
   * Take and remove the next job from the queue
   * @return
   */
  def next(): Option[J] = {
    if (realQueue.size > 0) return Some(realQueue.dequeue())
    None
  }
}

trait ScalaQueue[J <: Job] extends Queue[J] {
  val _realQueue = new mutable.Queue[J]()

  def realQueue: mutable.Queue[J] = _realQueue
}
