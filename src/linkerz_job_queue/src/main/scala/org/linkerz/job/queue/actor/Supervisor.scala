/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.actor

import akka.actor.{Props, ActorRef, Actor}
import org.linkerz.job.queue.handler.AsyncHandler.{StartSupervisor, Stop, Progress}
import grizzled.slf4j.Logging

/**
 * The Class Supervisor.
 *
 * @author Nguyen Duc Dung
 * @since 10/14/12 3:03 PM
 *
 */
class Supervisor extends Actor with Logging {

  private var isStop = false
  private var _reason: String = _
  private var _expectedJobNumber = -1

  protected def receive = {
    case StartSupervisor(job, manager) => {
      val startTime = System.currentTimeMillis()
      val timer = context.actorOf(Props(new Actor {
        protected def receive = {
          case timeOut: Int => {
            while (!isStop) {
              val jobTime = System.currentTimeMillis() - startTime
              if (timeOut > 0 && jobTime > timeOut) {
                isStop = true
                job.error("Time Out")
                stop("Time Out", manager)
              }
              Thread.sleep(100)
            }
          }
        }
      }))

      timer ! job.timeOut
    }
    case Progress(jobDone) => {
      if (isStop && _expectedJobNumber == jobDone) {
        stop(_reason, sender)
      }
    }
    case Stop(reason, expectedJobNumber) => {
      isStop = true
      _reason = reason
      _expectedJobNumber = expectedJobNumber
    }
  }

  protected def stop(reason: String, manager: ActorRef) {
    context.stop(manager)
    while (!manager.isTerminated) Thread.sleep(1000)
    info(reason)
    context.stop(self)
  }
}
