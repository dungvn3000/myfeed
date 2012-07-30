/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core._
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import grizzled.slf4j.Logging
import scala.Some

/**
 * The Class TestSimpleController.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 11:23 PM
 *
 */

case class EchoJob(message: String) extends Job {
  private var _result: String = _

  def result_=(result: String) {
    _result = result
  }

  def result: Option[String] = {
    if (_result != null && !_result.isEmpty) return Some(_result)
    None
  }
}

class EchoHandler extends Handler[EchoJob] with Logging {
  def accept(job: Job) = job.isInstanceOf[EchoJob]

  protected def doHandle(job: EchoJob) {
    info(job.message)
    job.result = "DONE"
    //Delay
    Thread.sleep(1000)
  }
}

case class SumJob(x: Int, y: Int) extends Job {
  private var _result: Int = _

  def result_=(result: Int) {
    _result = result
  }

  def result = {
    Some(_result)
  }
}

class SumHandler extends Handler[SumJob] with Logging {
  def accept(job: Job) = job.isInstanceOf[SumJob]
  protected def doHandle(job: SumJob) {
    job.result = job.x + job.y
    info(job.result)
  }
}

class InSessionJob extends Job {
  private var _result: Session = _
  def result_=(result: Session) {
    _result = result
  }
  def result = {
    Some(_result)
  }
}

class TestSession extends Session with Logging {
  val id = "TestSession"

  def openSession() = {
    info("openSession")
    this
  }

  def endSession() {
    info("endSession")
  }
}

class HandlerWithSession extends HandlerInSession[InSessionJob, TestSession] with Logging {

  def sessionClass = classOf[TestSession]

  def accept(job: Job) = job.isInstanceOf[InSessionJob]

  protected def doHandle(job: InSessionJob, session: TestSession) {
    job.result = session
    session match {
      case session: TestSession => info("Doing in session " + session.id)
    }
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

    controller.handlers = List(echoHandler, sumHandler, handlerWithSession)

    controller.add(echoJob)
    controller.add(sumJob)
    controller.add(inSessionJob)

    controller.start()

    Thread.sleep(2000)

    controller.stop()

    assert(echoJob.result.get == "DONE")
    assert(sumJob.result.get == 3)
    assert(inSessionJob.result.get != null)
  }
}