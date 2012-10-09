/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.controller

import org.linkerz.job.queue.core._
import org.linkerz.job.queue.core.Controller._
import grizzled.slf4j.Logging
import collection.JavaConversions._
import akka.actor.{Actor, Props}

/**
 * The Class BaseController.
 * The controller will handle the job in sync or async depend on the handler.
 * The controller will be never stop it will be wait for job for all his life
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:40 PM
 *
 */
class BaseController extends Controller with Logging {

  var handlers: List[Handler[_ <: Job]] = Nil

  /**
   * Only using for spring bean. Hopefully the spring framework is gonna support scala.
   */
  def setHandlers(springHandlers: java.util.List[Handler[_ <: Job]]) {
    handlers = springHandlers.toList
  }

  //The handler actor to handle all the handler
  val handlerActor = systemActor.actorOf(Props(new Actor {
    protected def receive = {
      case job: Job => {
        try {
          if (handleJob(job)) {
            //Change Job Status.
            if (!job.isError) {
              job.status = JobStatus.DONE
            }
          }
        } catch {
          case ex: Exception => handleError(job, ex)
        }
      }
      case "stop" => context.stop(self)
    }

    private def handleJob(job: Job): Boolean = {
      var isDone = false
      handlers.foreach(handler => if (handler accept job) {
        handler match {
          case handler: HandlerInSession[Job, Session[Job]] => {
            //Start handling with session.
            val session = handler.sessionClass.newInstance()
            session.openSession(job)
            handler.handle(job, session)
            session.endSession()
          }
          case _ => handler.handle(job)
        }
        //Mark the job was done.
        isDone = true
      })
      isDone
    }

    private def handleError(job: Job, ex: Exception) {
      error(ex.getMessage, ex)
      if (job != null) {
        //marking the error job
        job.error(ex.getMessage, ex)
      }
    }

  }), "handlerActor")

  /**
   * Start the controller
   */
  def start() {}


  /**
   * Stop the controller.
   */
  def stop() {
    handlerActor ! "stop"
    while (!handlerActor.isTerminated) Thread.sleep(1000)
    info("Stoped.")
  }

  /**
   * Add a job to the queue
   * @param job
   */
  def !(job: Job) {
    handlerActor ! job
  }
}
