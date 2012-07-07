/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller

import org.linkerz.job.queue.core._
import grizzled.slf4j.Logging
import actors.DaemonActor
import collection.mutable.ListBuffer
import scala.Some

/**
 * The Class BaseController.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:40 PM
 *
 */

class BaseController extends Controller with Logging {

  val workers = new ListBuffer[Worker[_ <: Job]]
  val jobQueue = new JobQueue

  //The handler actor to handle all the worker
  val handlerActor = new DaemonActor {
    def act() {
      loop {
        doJobs()
      }
    }
  }

  /**
   * Start the controller
   */
  def start() {
    handlerActor.start()
  }

  private def doJobs() {
    jobQueue.next() match {
      case Some(job) => {
        workers.foreach(worker => if (worker.isFor(job)) worker.work(job, null))
      }
      case None => logger.info("Nothing to do")
    }
  }
}
