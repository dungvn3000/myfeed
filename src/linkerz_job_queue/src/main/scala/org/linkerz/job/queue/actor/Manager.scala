package org.linkerz.job.queue.actor

import akka.actor.{ActorContext, ActorRef, Actor}
import org.linkerz.job.queue.handler.AsyncHandler._
import grizzled.slf4j.Logging
import org.linkerz.job.queue.core.Job
import org.linkerz.job.queue.handler.AsyncHandler.Success
import org.linkerz.job.queue.handler.AsyncHandler.Fail
import org.linkerz.job.queue.handler.AsyncHandler.Progress

/**
 * The Class Manager.
 *
 * @author Nguyen Duc Dung
 * @since 10/14/12 2:29 PM
 *
 */
class Manager[J <: Job](supervisor: ActorRef, createWorker: (ActorContext) => ActorRef, doJob: (J, ActorRef) => Unit,
                        onError: (String, Exception) => Unit, onSuccess: (J) => Unit) extends Actor with Logging {

  private val worker = createWorker(context)
  private var jobDone = 0

  protected def receive = {
    case job: J => {
      try {
        doJob(job, worker)
      } catch {
        case ex: Exception => onError(ex.getMessage, ex)
      }
    }
    case f: Fail[J] => {
      jobDone += 1
      supervisor ! Progress(jobDone)
      onError(f.ex.getMessage, f.ex)
    }
    case s: Success[J] => {
      jobDone += 1
      supervisor ! Progress(jobDone)
      onSuccess(s.job)
    }
  }
}
