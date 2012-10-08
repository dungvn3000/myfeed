/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import grizzled.slf4j.Logging
import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.pattern.ask
import org.linkerz.job.queue.handler.AsyncHandler.{Stop, Fail, Success, Next}
import akka.util.Timeout
import akka.util.duration._

object AsyncHandler {

  class Event

  case class Next[J <: Job, S <: Session[J]](job: J, session: S) extends Event

  case class Success[J <: Job](job: J) extends Event

  case class Fail[J <: Job](job: J, ex: Exception) extends Event

  object Stop extends Event

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

  //The flag will turn on when the worker manager is free.
  protected var isManagerFree: Boolean = _

  /**
   * The handler will stop when it turn on.
   */
  protected var isStop: Boolean = _

  /**
   * The current session.
   */
  protected var currentSession: S = _

  /**
   * The current job inside the session.
   */
  protected var currentJob: J = _

  /**
   * Local worker.
   */
  protected val worker: ActorRef = null

  val system = ActorSystem("system")

  private implicit val timeout = Timeout(5 seconds)

  //The boss of all workers.
  protected implicit val workerManager = system.actorOf(Props(new Actor {
    override protected def receive = {
      case job: J => {
        try {
          isManagerFree = false
          doJob(job)
        } catch {
          case ex: Exception => {
            error(ex.getMessage, ex)
            currentJob.error(ex.getMessage, ex)
          }
        } finally {
          isManagerFree = true
        }
      }
      case s: Success[J] => {
        //Counting jobs have done.
        currentSession.subJobCount += 1
        onSuccess(s.job)
      }
      case f: Fail[J] => {
        error(f.ex.getMessage, f.ex)
        currentJob.error(f.ex.getMessage, f.ex)
      }
      case Stop => {
        worker ? Stop
        context.stop(self)
      }
    }
  }), "workerManager")

  protected def doHandle(job: J, session: S) {
    //Reset to start
    isStop = false
    isManagerFree = true
    currentSession = session
    currentJob = job

    workerManager ! job

    //Step 3: Waiting for all worker finished their job
    waitingForFinish()

    //Step 4: Finish.
    onFinish()
  }

  private def waitingForFinish() {
    while (!workerManager.isTerminated) {
      //Checking working time.
      if (currentJob.timeOut > 0) {
        if (currentSession.jobTime > currentJob.timeOut) {
          info("Stop because the time is out")
          //marking the job is error
          currentJob.error("Time Out")
          workerManager ? Stop
        }
      }

      //Sleep 1s for next checking.
      Thread.sleep(1000)
    }
  }

  private def doJob(job: J) {
    //Step 1: Checking should go for the job or not
    if (currentJob.maxSubJob >= 0 && currentSession.subJobCount >= currentJob.maxSubJob) {
      info("Stop because the number of sub job reached maximum")
      workerManager ? Stop
      return
    }

    if (job.depth > currentSession.currentDepth) {
      currentSession.currentDepth = job.depth
    }

    if (currentSession.currentDepth > currentJob.maxDepth && currentJob.maxDepth > 0) {
      info("Stop because the number of sub job reached maximum depth")
      currentSession.currentDepth -= 1
      workerManager ? Stop
      return
    }

    //Step 2: Find a free worker for the job.
    worker ! Next(job, currentSession)

    //Delay time for each job.
    if (currentJob.politenessDelay > 0) Thread.sleep(currentJob.politenessDelay)
  }

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
