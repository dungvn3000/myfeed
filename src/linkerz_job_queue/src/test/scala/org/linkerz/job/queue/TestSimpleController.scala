/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.{Job, Session, Worker}
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import actors.Actor

/**
 * The Class TestSimpleController.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 11:23 PM
 *
 */

case class EchoJob(var message: String) extends Job {
  def get() = {
    Some(message)
  }
}

@RunWith(classOf[JUnitRunner])
class TestSimpleController extends FunSuite {


  test("testSimpleController") {
    val controller = new BaseController

    val echoJob = new EchoJob("Hello Frist Task")

    val worker = new Worker[EchoJob] {
      def doWork(job: EchoJob, session: Session) {
        job.get() match {
          case Some(st) => {
            println(st)
            job.message = "I'm pro"
          }
        }
      }
    }

    controller.workers += worker

    controller.start()

    controller.jobQueue.add(echoJob)


    val monitorActor = new Actor {
      def act() {
      }
    }
    monitorActor.start()
  }
}