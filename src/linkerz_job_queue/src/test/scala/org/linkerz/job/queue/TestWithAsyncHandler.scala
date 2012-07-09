/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core._
import handler.AsyncHandler
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import grizzled.slf4j.Logging
import collection.mutable.ListBuffer
import util.Random
import scala.Some

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
    for (i <- 1 to 10) {
      val subJob = new JobHasSubJob {
        override def get() = {
          Thread.sleep(Random.nextInt(10000))
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

class TestWorker(id: Int) extends Worker[JobHasSubJob, TestSession] with Logging {

  def analyze(job: JobHasSubJob, session: TestSession) = {
    info("Worker " + id + " is working...")
    val subJobs = job.get() match {
      case Some(jobs) => jobs.toList
    }
    subJobs
  }
}

class TestAsyncHandler extends AsyncHandler[JobHasSubJob, TestSession] {

  def accept(job: Job) = job.isInstanceOf[JobHasSubJob]

  def sessionClass = classOf[TestSession]

}

@RunWith(classOf[JUnitRunner])
class TestWithAsyncHandler extends FunSuite with BeforeAndAfter with Logging {

  var job: JobHasSubJob = _
  var worker: TestWorker = _
  var handler: TestAsyncHandler = _

  before {
    job = new JobHasSubJob
    worker = new TestWorker(0)
    handler = new TestAsyncHandler
  }

  test("testJobHasSubJob") {
    assert(job.get().get.size == 10)
    assert(job.get().get.head.parent.get == job)
  }

  test("testWorker") {
    val subJobs = worker.analyze(job, null)
    assert(subJobs.size == 10)
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
    //Add worker to the handler
    for (i <- 1 to 10) {
      handler.workers += new TestWorker(i)
    }
    controller.handlers += handler

    controller.add(job)

    controller.start()

    Thread.sleep(10000)

    controller.stop()
  }

}
