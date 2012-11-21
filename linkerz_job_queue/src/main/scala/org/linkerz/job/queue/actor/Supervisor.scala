/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.actor

import akka.actor.{Props, ActorRef, Actor}
import grizzled.slf4j.Logging
import org.linkerz.job.queue.event.LocalEvents.{Progress, StartWatching}

/**
 * The Class Supervisor.
 *
 * @author Nguyen Duc Dung
 * @since 10/14/12 3:03 PM
 *
 */
class Supervisor extends Actor with Logging {

  private var isStop = false

  protected def receive = {
    case StartWatching(job, manager) => {
      val startTime = System.currentTimeMillis()
      val timer = context.actorOf(Props(new Actor {
        protected def receive = {
          case timeOut: Int => {
            while (!isStop) {
              val jobTime = System.currentTimeMillis() - startTime
              if (timeOut > 0 && jobTime > timeOut) {
                isStop = true
                job.error("Time Out", getClass.getName)
                stop(manager)
              }
              Thread.sleep(100)
            }
          }
        }
      }))

      timer ! job.timeOut
    }
    case Progress(percent) => {
      if (percent == 100) {
        stop(sender)
      }
    }
  }

  protected def stop(manager: ActorRef) {
    isStop = true
    context.stop(manager)
    while (!manager.isTerminated) Thread.sleep(1000)
    context.stop(self)
  }
}
