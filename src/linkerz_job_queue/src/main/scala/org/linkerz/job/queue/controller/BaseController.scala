/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller

import org.linkerz.job.queue.core._
import grizzled.slf4j.Logging
import actors.DaemonActor
import reflect.BeanProperty

/**
 * The Class BaseController.
 * The controller will handle the job in sync.
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

  @BeanProperty var handlers: List[Handler[_ <: Job]] = Nil

  //The handler actor to handle all the handler
  val handlerActor = new DaemonActor {
    def act() {
      loop {
        react {
          case NEXT(job) => handleJob(job)
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
  def add(job: Job) {
    handlerActor ! NEXT(job)
  }

  private def handleJob(job: Job) {
    handlers.foreach(handler => if (handler accept job) {
      handler match {
        case handler: HandlerInSession[Job, Session] => {
          //Start handling with session.
          val session = handler.sessionClass.newInstance()
          session.openSession()
          handler.handle(job, session)
          session.endSession()
        }
        case _ => handler.handle(job)
      }
    })
  }
}
