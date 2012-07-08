/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.{Job, Session, Handler}
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

/**
 * The Class TestSimpleController.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 11:23 PM
 *
 */

case class EchoJob(message: String) extends Job {
  def get() = {
    Some(message)
  }
}

class EchoHandler extends Handler[EchoJob] {

  def accept(job: Job) = job.isInstanceOf[EchoJob]

  def doHandle(job: EchoJob, session: Session) {
    println(job.message)
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

class SumHandler extends Handler[SumJob] {

  def accept(job: Job) = job.isInstanceOf[SumJob]

  def doHandle(job: SumJob, session: Session) {
    job.result = job.x + job.y
    println(job.result)
  }
}

@RunWith(classOf[JUnitRunner])
class TestSimpleController extends FunSuite {


  test("testSimpleController") {
    val controller = new BaseController

    val echoJob = new EchoJob("Hello Frist Task")
    val echoWorker = new EchoHandler

    val sumJob = new SumJob(1, 2)
    val sumWorker = new SumHandler

    controller.handlers += echoWorker
    controller.handlers += sumWorker

    controller.add(echoJob)
    controller.add(sumJob)

    controller.start()

    Thread.sleep(2000)
  }
}