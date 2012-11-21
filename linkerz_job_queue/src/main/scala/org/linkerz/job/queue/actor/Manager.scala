package org.linkerz.job.queue.actor

import akka.actor.{ActorContext, ActorRef, Actor}
import akka.util.duration._
import grizzled.slf4j.Logging
import org.linkerz.job.queue.core.Job
import org.linkerz.job.queue.event.Events._

/**
 * The Class Manager.
 *
 * @author Nguyen Duc Dung
 * @since 10/14/12 2:29 PM
 *
 */
class Manager[J <: Job](supervisor: ActorRef, createWorker: (ActorContext) => ActorRef, doJob: (J, ActorRef) => Unit,
                        onError: (J, String, Exception) => Unit, onSuccess: (J) => Unit) extends Actor with Logging {

  private val worker = createWorker(context)

  private var jobReceive = 0
  private var jobDone = 0

  protected def receive = {
    case job: J => {
      try {
        jobReceive += 1
        doJob(job, worker)
      } catch {
        case ex: Exception => onError(job, ex.getMessage, ex)
      }
    }
    case f: Fail[J] => {
      onError(f.job, f.ex.getMessage, f.ex)
      jobDone += 1
      reportToSupervisor()
    }
    case s: Success[J] => {
      onSuccess(s.job)
      jobDone += 1
      reportToSupervisor()
    }
  }

  def reportToSupervisor() {
    if (percent == 100) {
      //Waiting for 3 seconds and recheck again
      context.system.scheduler.scheduleOnce(3 seconds) {
        supervisor ! Progress(percent)
      }
    } else supervisor ! Progress(percent)
  }

  def percent = jobDone / jobReceive * 100
}
