/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue

import controller.BaseController
import core.JobStatus
import exception.TestException
import handler.{AsyncTestHandler, ErrorSyncHandler, SyncHandler}
import job.{EmptyJob, EchoJob, SumJob}
import org.junit.Test
import junit.framework.Assert
import org.scalatest.FunSuite

/**
 * The Class BaseControllerSuite.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 3:27 AM
 *
 */
class BaseControllerSuite extends FunSuite {

  test("test a job has 10 subjobs") {
    val controller = new BaseController
    val handler = new AsyncTestHandler(10)
    controller.handlers = List(handler)
    controller.start()
    val job = new EmptyJob

    controller ! job

    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
    Assert.assertEquals(11, job.result.get)
  }

  test("test controller with no handler") {
    val controller = new BaseController
    controller.start()

    val sumJob = SumJob(1, 2)
    controller ! sumJob

    controller.stop()
    Assert.assertEquals(JobStatus.NEW, sumJob.status)
  }

  test("test with sync handler") {
    val controller = new BaseController
    controller.handlers = List(new SyncHandler)
    controller.start()
    val sumJob1 = SumJob(1, 2)
    val sumJob2 = SumJob(3, 4)
    controller ! sumJob1
    controller ! sumJob2
    controller.stop()

    Assert.assertEquals(3, sumJob1.result.get)
    Assert.assertEquals(JobStatus.DONE, sumJob1.status)

    Assert.assertEquals(7, sumJob2.result.get)
    Assert.assertEquals(JobStatus.DONE, sumJob2.status)
  }

  test("test with sync error handler") {
    val controller = new BaseController
    controller.handlers = List(new ErrorSyncHandler)
    controller.start()
    val echo = EchoJob("Hello")
    controller ! echo
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, echo.status)
    Assert.assertEquals(classOf[TestException].getName, echo.errors.head.exceptionClass.get)
  }

  ignore("test async handler with 10000 jobs") {
    val controller = new BaseController
    val handler = new AsyncTestHandler
    controller.handlers = List(handler)
    controller.start()
    val job = new EmptyJob
    job.maxSubJob = 10000

    controller ! job

    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
    Assert.assertEquals(job.maxSubJob, job.result.get)
  }

  test("test time out") {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()
    val job = new EmptyJob
    job.timeOut = 5000

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, job.status)
  }

  test("test max depth") {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()
    val job = new EmptyJob
    job.maxDepth = 1

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
    Assert.assertEquals(1001, job.result.get)
  }

  ignore("test async handler with 10 jobs") {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()

    for (i <- 0 to 9) {
      val job = new EmptyJob
      job.maxSubJob = 1000
      controller ? job
      Assert.assertEquals(JobStatus.DONE, job.status)
      Assert.assertEquals(1000, job.result.get)
    }

    controller.stop()
  }

  test("test multi handler") {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler, new SyncHandler, new ErrorSyncHandler)
    controller.start()

    val echoJob = EchoJob("Hello")
    val sumJob = SumJob(4, 5)
    val emptyJob = new EmptyJob
    emptyJob.maxSubJob = 20

    controller ! echoJob
    controller ! sumJob
    controller ! emptyJob

    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, echoJob.status)
    Assert.assertEquals(9, sumJob.result.get)
    Assert.assertEquals(JobStatus.DONE, sumJob.status)
    Assert.assertEquals(emptyJob.maxSubJob, emptyJob.count)
  }

  test("test sync handler controller") {
    val controller = new BaseController
    controller.handlers = List(new SyncHandler)
    controller.start()
    val sumJob1 = SumJob(1, 2)
    val sumJob2 = SumJob(3, 4)

    controller ? sumJob1
    Assert.assertEquals(3, sumJob1.result.get)
    Assert.assertEquals(JobStatus.DONE, sumJob1.status)

    controller ? sumJob2

    Assert.assertEquals(7, sumJob2.result.get)
    Assert.assertEquals(JobStatus.DONE, sumJob2.status)
    controller.stop()
  }

}