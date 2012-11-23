/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.controller

import org.linkerz.job.queue.core._
import org.linkerz.job.queue.core.Controller._
import grizzled.slf4j.Logging
import akka.actor.{Actor, Props}
import org.linkerz.job.queue.event.RemoteEvents._
import org.linkerz.job.queue.event.RemoteEvents.Login
import org.linkerz.job.queue.event.RemoteEvents.LoginOk
import org.linkerz.job.queue.event.RemoteEvents.Logout
import org.linkerz.core.conf.AppConfig
import akka.pattern.ask
import akka.dispatch.Await
import akka.util.duration._
import akka.util.Timeout

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

  //This will be assigned by server.
  var id: Int = _

  val serverActor = systemActor.actorFor(AppConfig.monitorActorPath)

  //The handler actor to handle all the handler
  implicit val handlerActor = systemActor.actorOf(Props(new Actor {
    protected def receive = {
      case job: Job => {
        //Notify server, we are processing this job.
        serverActor ! Processing(job)
        handleJob(job)
      }
      case "stop" => context.stop(self)
      case Restart => {
        //TODO @dungvn3000
      }
    }
  }))

  private def handleJob(job: Job) {
    var isDone = false
    try {
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
      if (isDone && !job.isError) job.status = JobStatus.DONE
    } catch {
      case ex: Exception => handleError(job, ex)
    }
  }

  protected def handleError(job: Job, ex: Exception) {
    error(ex.getMessage, ex)
    serverActor ! Error(job, ex.getMessage)
    if (job != null) {
      //marking the error job
      job.error(ex.getMessage, getClass.getName, ex)
    }
  }

  /**
   * Start the controller
   */
  def start() {
    implicit val timeout = Timeout(15 seconds)
    try {
      Await.result(serverActor ? Login("Hello"), timeout.duration) match {
        case LoginOk(_id) => {
          id = _id
          info("Login Ok with id " + _id)
        }
        case Reject(msg) => {
          info("Server reject login request with reason " + msg)
          stop()
          return
        }
      }
    } catch {
      case ex: Exception => {
        error(ex.getMessage, ex)
        stop()
        return
      }
    }
  }


  /**
   * Stop the controller.
   */
  def stop() {
    handlerActor ! "stop"
    serverActor ! Logout("Goodbye")
    while (!handlerActor.isTerminated) Thread.sleep(1000)
    systemActor.shutdown()
    systemActor.awaitTermination()
    info("Stoped.")
  }

  /**
   * Add a job to the queue
   * @param job
   */
  def !(job: Job) {
    handlerActor ! job
  }

  /**
   * Sync doing the job.
   * @param job
   */
  def ?(job: Job) {
    handleJob(job)
  }
}
