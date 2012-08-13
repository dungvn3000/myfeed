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
import scala.Some
import collection.mutable.ListBuffer

/**
 * The Class TestWithBaseHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 2:16 AM
 *
 */

class JobHasSubJob extends Job {
  var subJobs: List[JobHasSubJob] = Nil
  private var _parent: JobHasSubJob = _

  def result: Option[List[JobHasSubJob]] = {
    if (!subJobs.isEmpty) return Some(subJobs)
    None
  }

  override def parent: Option[JobHasSubJob] = {
    if (_parent != null) {
      return Some(_parent)
    }
    None
  }

  def parent_=(parent: JobHasSubJob) {
    _parent = parent
  }
}

class JobHasSubJobSession extends Session[JobHasSubJob] with Logging {
  val id = "JobHasSubJobSession"

  def openSession(job: JobHasSubJob) = {
    info("openSession")
    this
  }

  override def endSession() {
    info("endSession")
  }
}

class TestWorker(id: Int) extends Worker[JobHasSubJob, JobHasSubJobSession] with Logging {
  def analyze(job: JobHasSubJob, session: JobHasSubJobSession) {
    info("Worker " + id + " is working...")
    if (job.parent.isEmpty) {
      //Only add sub job for first job
      val subJobs = new ListBuffer[JobHasSubJob]
      for (i <- 1 to 10) {
        val subJob = new JobHasSubJob
        subJob.parent = job
        subJobs += subJob
      }
      job.subJobs = subJobs.toList
    }
  }
}

class TestAsyncHandler extends AsyncHandler[JobHasSubJob, JobHasSubJobSession] {
  def accept(job: Job) = job.isInstanceOf[JobHasSubJob]
  def sessionClass = classOf[JobHasSubJobSession]
  protected def createSubJobs(job: JobHasSubJob) {
    job.result match {
      case Some(subJob) => subJobQueue ++= subJob
      case None =>
    }
  }
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

  test("testWorker") {
    worker.analyze(job, null)
    assert(!job.result.isEmpty)
    assert(job.result.get.size == 10)
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
    controller.handlers = List(handler)
    controller.add(job)
    controller.start()
    Thread.sleep(10000)
    controller.stop()
    assert(!job.result.isEmpty)
    assert(job.result.get.size == 10)
  }

}
