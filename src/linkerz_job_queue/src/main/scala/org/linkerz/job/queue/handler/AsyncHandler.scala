/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import org.linkerz.job.queue.core.Controller._
import grizzled.slf4j.Logging
import akka.actor._
import org.linkerz.job.queue.handler.AsyncHandler.{Stop, Success, Fail, Next}
import akka.routing.Broadcast

object AsyncHandler {

  sealed trait Event

  case class Next[J <: Job, S <: Session[J]](job: J, session: S) extends Event

  case class Success[J <: Job](job: J) extends Event

  case class Fail[J <: Job](job: J, ex: Exception) extends Event

  case class Stop(reason: String) extends Event

}

/**
 * The Class AsyncHandler.
 * The Handler with workers, the job will be do async in here.
 * Make sure the handler always has at least one worker.
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
   * The worker manager.
   */
  private var workerManager: ActorRef = _

  /**
   * Hard working worker.
   */
  private var worker: ActorRef = _

  protected def doHandle(job: J, session: S) {
    //Step 1: Reset to start
    isStop = false
    currentSession = session
    currentJob = job
    workerManager = systemActor.actorOf(Props(createManager()))

    //Step 2: Send to the worker manager.
    this ! job

    //Step 3: Waiting for all worker finished their job
    waitingForFinish()

    //Step 4: Finish.
    onFinish()
  }

  private def waitingForFinish() {
    while (!workerManager.isTerminated) {
      //Sleep 1s for next checking.
      Thread.sleep(1000)
    }
  }

  /**
   * Send a job to the manager
   * @param job
   */
  protected def !(job: J) {
    if (!isStop && shouldDo(job)) workerManager ! job
  }

  /**
   * Considering should we go for it or not.
   * @param job
   * @return
   */
  protected def shouldDo(job: J): Boolean = {
    //Step 1: Checking whether go for the job or not
    if (currentJob.maxSubJob >= 0 && currentSession.subJobCount >= currentJob.maxSubJob) {
      stop("Stop because the number of sub job reached maximum")
      return false
    }

    if (currentSession.currentDepth > currentJob.maxDepth && currentJob.maxDepth > 0) {
      currentSession.currentDepth -= 1
      stop("Stop because the number of sub job reached maximum depth")
      return false
    }

    //Checking working time.
    if (currentJob.timeOut > 0 && currentSession.jobTime > currentJob.timeOut) {
      //marking the job is error
      currentJob.error("Time Out")
      stop("Stop because the time is out")
      return false
    }

    true
  }

  /**
   * Send the job to the worker.
   * @param job
   */
  protected def doJob(job: J) {

    worker.tell(Next(job, currentSession), workerManager)

    //Counting.
    currentSession.subJobCount += 1

    //Store the maximum depth level.
    if (job.depth > currentSession.currentDepth) {
      currentSession.currentDepth = job.depth
    }

    //Delay time for each job.
    if (currentJob.politenessDelay > 0) Thread.sleep(currentJob.politenessDelay)
  }

  /**
   * Stop the handler with a reason.
   * @param reason
   */
  protected def stop(reason: String) {
    isStop = true
    worker ! Broadcast(Stop(reason))
    while (!worker.isTerminated) Thread.sleep(1000)
    workerManager ! Stop(reason)
  }

  /**
   * Create the manager actor.
   */
  protected def createManager() = {
    new Actor {

      worker = createWorker(context)

      override protected def receive = {
        case job: J => {
          try {
            doJob(job)
          } catch {
            case ex: Exception => {
              error(ex.getMessage, ex)
              currentJob.error(ex.getMessage, ex)
            }
          }
        }
        case f: Fail[J] => {
          error(f.ex.getMessage, f.ex)
          currentJob.error(f.ex.getMessage, f.ex)
        }
        case s: Success[J] => onSuccess(s.job)
        case Stop(reason) => {
          info(reason)
          context.stop(self)
        }
      }
    }
  }

  /**
   * Creating worker.
   * @param context
   * @return
   */
  protected def createWorker(context: ActorContext): ActorRef

  /**
   * This method will be called before the hander is going to finish everything.
   */
  protected def onFinish() {}

  /**
   * Create a sub job base the result of a job.
   * @param job
   */
  protected def onSuccess(job: J)
}
