/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller

import org.linkerz.job.queue.core._
import grizzled.slf4j.Logging
import actors.DaemonActor
import collection.JavaConversions._

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

  /**
   * Object will be sent when the job was be done.
   */
  case object DONE

  case object STOP

  case class NEXT(job: Job)

  var handlers: List[Handler[_ <: Job]] = Nil

  /**
   * Only using for spring bean. Hopefully spring is gonna support scala.
   */
  def setHandlers(springHandlers: java.util.List[Handler[_ <: Job]]) {
    handlers = springHandlers.toList
  }

  //The handler actor to handle all the handler
  val handlerActor = new DaemonActor {
    def act() {
      loop {
        react {
          case NEXT(job) => {
            try {
              handleJob(job)
            } catch {
              case ex: Exception => handleError(job, ex)
            }
          }
          case STOP => {
            info("Stoping...")
            reply("Stoping...")
            exit()
          }
        }
      }
    }
  }

  /**
   * Start the controller
   */
  def start() {
    handlerActor.start()
  }


  /**
   * Stop the controller.
   */
  def stop() {
    handlerActor !? STOP
    info("Stoped.")
  }


  /**
   * Add a job to the queue
   * @param job
   */
  def !(job: Job) {
    handlerActor ! NEXT(job)
  }

  protected def handleJob(job: Job) {
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
    })
  }

  /**
   * Handle error for the job
   * @param job
   * @param ex
   */
  protected def handleError(job: Job, ex: Exception) {
    error(ex.getMessage, ex)
    //The logger is not working probably.
    ex.printStackTrace()
  }
}
