/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.{Job, Session, Handler}
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import grizzled.slf4j.Logging

/**
 * The Class TestSimpleController.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 11:23 PM
 *
 */

case class EchoJob(message: String) extends Job {
  var result: String = _

  def get(): Option[String] = {
    if (result != null && !result.isEmpty) return Some(result)
    None
  }
}

class EchoHandler extends Handler[EchoJob] with Logging {

  def accept(job: Job) = job.isInstanceOf[EchoJob]

  protected def doHandle(job: EchoJob, session: Session) {
    info(job.message)
    job.result = "DONE"
    //Delay
    Thread.sleep(1000)
  }
}

case class SumJob(x: Int, y: Int) extends Job {
  var result: Int = _

  def get() = {
    Some(result)
  }
}

class SumHandler extends Handler[SumJob] with Logging {

  def accept(job: Job) = job.isInstanceOf[SumJob]

  protected def doHandle(job: SumJob, session: Session) {
    job.result = job.x + job.y
    info(job.result)
  }
}

class InSessionJob extends Job {

  var result: Session = _

  def get() = {
    Some(result)
  }
}

class HandlerWithSession extends Handler[InSessionJob] with Session with Logging{

  val session = new Session {
    def endSession() {
      info("endSession")
    }

    def openSession() = {
      info("openSession")
      this
    }
  }

  def openSession() = session.openSession()

  def endSession() {
    session.endSession()
  }

  def accept(job: Job) = job.isInstanceOf[InSessionJob]

  protected def doHandle(job: InSessionJob, session: Session) {
    job.result = session
    info("Doing in session " + job.result)
  }
}

@RunWith(classOf[JUnitRunner])
class TestSimpleController extends FunSuite {


  test("testSimpleController") {
    val controller = new BaseController

    val echoJob = new EchoJob("Hello Frist Task")
    val echoHandler = new EchoHandler

    val sumJob = new SumJob(1, 2)
    val sumHandler = new SumHandler

    val inSessionJob = new InSessionJob
    val handlerWithSession = new HandlerWithSession

    controller.handlers += echoHandler
    controller.handlers += sumHandler
    controller.handlers += handlerWithSession

    controller.add(echoJob)
    controller.add(sumJob)
    controller.add(inSessionJob)

    controller.start()

    Thread.sleep(2000)

    controller.stop()

    assert(echoJob.get().get == "DONE")
    assert(sumJob.get().get == 3)
    assert(inSessionJob.get().get != null)
  }
}