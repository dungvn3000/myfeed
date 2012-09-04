/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.JobStatus
import exception.TestException
import handler.{AsyncTestHandler, ErrorSyncHandler, SyncHandler}
import job.{EmptyJob, EchoJob, SumJob}
import org.junit.{Ignore, Test}
import junit.framework.Assert
import org.junit.experimental.categories.Category
import org.linkerz.test.categories.ManualTest

/**
 * The Class TestBaseController.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 3:27 AM
 *
 */
class TestBaseController {

  @Test
  def testWithNoHandler() {
    val controller = new BaseController
    controller.start()

    val sumJob = SumJob(1, 2)
    controller ! sumJob

    controller.stop()
    Assert.assertEquals(JobStatus.NEW, sumJob.status)
  }

  @Test
  def testWithSyncHandler() {
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

  @Test
  def testWithErrorSyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new ErrorSyncHandler)
    controller.start()
    val echo = EchoJob("Hello")
    controller ! echo
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, echo.status)
    Assert.assertEquals(classOf[TestException], echo.error.head._2.getClass)
  }


  @Test
  def testWithAsyncHandler() {
    val controller = new BaseController
    val handler = new AsyncTestHandler
    controller.handlers = List(handler)
    controller.start()
    val job = new EmptyJob
    job.numberOfWorker = 1000
    job.maxSubJob = 1000

    controller ! job

    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
    Assert.assertEquals(job.maxSubJob, job.result.get)
  }

  @Test
  def testTimeOutJob() {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()
    val job = new EmptyJob
    job.numberOfWorker = 3
    job.timeOut = 5000

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, job.status)
  }

  //This test case is too slow, only run it by manually.
  @Test
  @Ignore
  @Category(Array(classOf[ManualTest]))
  def testWithAsyncHandlerAnd100Job() {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()

    var time = System.currentTimeMillis()
    for (i <- 0 to 99) {
      val job = new EmptyJob
      job.numberOfWorker = 1000
      job.maxSubJob = 1000

      controller ! job
    }

    controller.stop()
    time = System.currentTimeMillis() - time

    println("time = " + time)
  }


  @Test
  def testMultiHandler() {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler, new SyncHandler, new ErrorSyncHandler)
    controller.start()

    val echoJob = EchoJob("Hello")
    val sumJob = SumJob(4, 5)
    val emptyJob = new EmptyJob
    emptyJob.numberOfWorker = 10
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

}