/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import org.linkerz.job.queue.core.Controller._
import grizzled.slf4j.Logging
import akka.actor._
import org.linkerz.job.queue.actor.{Supervisor, Manager}
import org.linkerz.job.queue.event.LocalEvents.{Next, StartWatching}

/**
 * The Class AsyncHandler.
 * The Handler with workers, the job will be do async in here.
 * Make sure the handler always has at least one actor.
 * The Worker will be created before handle a job.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:56 AM
 *
 */
abstract class AsyncHandler[J <: Job, S <: Session[J]] extends HandlerInSession[J, S] with Logging {

  /**
   * This flag will be turn on when the manager is going to stop.
   * Please don't modify it outside the manager actor.
   */
  protected var isStop = false

  /**
   * The current session.
   */
  protected var currentSession: S = _

  /**
   * The current job inside the session.
   */
  protected var currentJob: J = _

  /**
   * The actor manager.
   */
  private var workerManager: ActorRef = _

  /**
   * Using for monitor work progress between manager and worker.
   */
  private var supervisor: ActorRef = _


  protected def doHandle(job: J, session: S) {
    //Step 1: Reset to start
    isStop = false
    currentSession = session
    currentJob = job
    supervisor = systemActor.actorOf(Props(new Supervisor))
    workerManager = systemActor.actorOf(Props(new Manager(supervisor, createWorker, doJob, onError, onSuccess)))

    supervisor ! StartWatching(job, workerManager)

    //Step 2: Send to the actor manager.
    this ! job

    //Step 3: Waiting for all actor finished their job.
    waitingForFinish()

    //Step 4: Finish.
    onFinish()
  }

  private def waitingForFinish() {
    while (!supervisor.isTerminated) {
      //Sleep 1s for next checking.
      Thread.sleep(1000)
    }
  }

  /**
   * Send a job to the manager
   * @param job
   */
  protected def !(job: J) {
    if (!isStop) {

      //Store the maximum depth level.
      if (job.depth > currentSession.currentDepth) {
        currentSession.currentDepth = job.depth
      }

      if (shouldDo(job)) {
        workerManager ! job
        //Counting.
        currentSession.subJobCount += 1
      }
    }
  }

  /**
   * Considering should we go for it or not.
   * @param job
   * @return
   */
  protected def shouldDo(job: J): Boolean = {
    //Step 1: Checking whether go for the job or not
    if (currentJob.maxSubJob >= 0 && currentSession.subJobCount >= currentJob.maxSubJob) {
      isStop = true
      return false
    }

    if (currentSession.currentDepth > currentJob.maxDepth && currentJob.maxDepth > 0) {
      currentSession.currentDepth -= 1
      isStop = true
      return false
    }

    true
  }

  /**
   * Send the job to the actor.
   * @param job
   * @param worker
   */
  protected def doJob(job: J, worker: ActorRef) {

    worker.tell(Next(job, currentSession), workerManager)

    //Delay time for each job.
    if (currentJob.politenessDelay > 0) Thread.sleep(currentJob.politenessDelay)
  }

  /**
   * Creating actor.
   * @param context
   * @return
   */
  protected def createWorker(context: ActorContext): ActorRef

  /**
   * This method will be called before the hander is going to finish everything.
   */
  protected def onFinish() {}

  /**
   * Call when a error happen.
   * @param message
   * @param ex
   */
  protected def onError(job: J,message: String, ex: Exception) {
    error(ex.getMessage, ex)
    currentJob.error(ex.getMessage, getClass.getName, ex)
  }

  /**
   * Create a sub job base the result of a job.
   * @param job
   */
  protected def onSuccess(job: J) {}
}
