/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.{Worker, Session, Job}
import handler.BaseHandler
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import grizzled.slf4j.Logging
import collection.mutable.ListBuffer
import util.Random

/**
 * The Class TestWithBaseHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 2:16 AM
 *
 */

class JobHasSubJob extends Job {
  var _parent: JobHasSubJob = _

  def get() = {
    val subJobs = new ListBuffer[JobHasSubJob]
    for (i <- 1 to 3) {
      val subJob = new JobHasSubJob {
        override def get() = {
          Thread.sleep(Random.nextInt(1000))
          //Return empty list.
          Some(new ListBuffer[JobHasSubJob])
        }
      }
      subJob._parent = this
      subJobs += subJob
    }
    Some(subJobs)
  }

  override def parent = Some(_parent)
}

class TestSession extends Session {

  def openSession() = null

  def endSession() {}
}

class TestWorker(id: Int) extends Worker[JobHasSubJob, TestSession] with Logging {

  var _isFree = true

  def isFree = _isFree

  def work(job: JobHasSubJob, session: TestSession) = {
    _isFree = false
    info("Worker " + id + " is working...")
    val subJobs = job.get() match {
      case Some(subJobs) => subJobs.toList
    }
    _isFree = true
    subJobs
  }
}

class TestHandler extends BaseHandler[JobHasSubJob, TestSession] {

  def accept(job: Job) = job.isInstanceOf[JobHasSubJob]

  def sessionClass = classOf[TestSession]

}

@RunWith(classOf[JUnitRunner])
class TestWithBaseHandler extends FunSuite with Logging {

  val job = new JobHasSubJob
  val worker = new TestWorker(0)
  val handler = new TestHandler

  test("testJobHasSubJob") {
    assert(job.get().get.size == 3)
    assert(job.get().get.head.parent.get == job)
  }

  test("testWorker") {
    val subJobs = worker.work(job, null)
    assert(subJobs.size == 3)
  }

  test("testHandlerWithBusyWorker") {
    for (i <- 1 to 3) {
      val busyWorker = new TestWorker(i) {
        override def isFree = false
      }
      handler.workers += busyWorker
    }
    handler.maxRetry = 3
    handler.handle(job, null)
    assert(handler.retryCount == 3)
  }

  test("testHandler") {
    for (i <- 1 to 3) {
      handler.workers += new TestWorker(i)
    }
    handler.handle(job, null)
  }

  test("testBaseHandler") {

    val controller = new BaseController
    //Add worker to handler
    for (i <- 1 to 3) {
      handler.workers += new TestWorker(i)
    }
    controller.handlers += handler

    controller.add(job)

    controller.start()

    Thread.sleep(3000)

    controller.stop()
  }

}
