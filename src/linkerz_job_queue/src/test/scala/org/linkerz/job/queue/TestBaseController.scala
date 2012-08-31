/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.JobStatus
import exception.TestException
import handler.{AsyncTestHandler, ErrorSyncHandler, SyncHandler}
import job.SumJob
import org.junit.Test
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
  def testWithSyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new SyncHandler)
    controller.start()
    val sumJob1 = SumJob(1, 2)
    val sumJob2 = SumJob(3, 4)
    controller ! sumJob1
    controller ! sumJob2
    controller.stop()

    Assert.assertEquals(sumJob1.result.get, 3)
    Assert.assertEquals(JobStatus.DONE, sumJob1.status)

    Assert.assertEquals(sumJob2.result.get, 7)
    Assert.assertEquals(JobStatus.DONE, sumJob2.status)
  }

  @Test
  def testWithErrorSyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new ErrorSyncHandler)
    controller.start()
    val sumJob = new SumJob(1, 2)
    controller ! sumJob
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, sumJob.status)
    Assert.assertEquals(classOf[TestException], sumJob.error.head._2.getClass)
  }


  @Test
  def testWithAsyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()
    val sumJob = new SumJob(1, 2)
    sumJob.numberOfWorker = 1000
    sumJob.maxRetry = 3

    controller ! sumJob

    controller.stop()

    Assert.assertEquals(sumJob.result.get, 3)
    Assert.assertEquals(JobStatus.DONE, sumJob.status)
  }

  //This test case two slow, only run it by manually.
  @Test
  @Category(Array(classOf[ManualTest]))
  def testWithAsyncHandlerAnd100Job() {
    val controller = new BaseController
    controller.handlers = List(new AsyncTestHandler)
    controller.start()

    var time = System.currentTimeMillis()
    for (i <- 0 to 99) {
      val sumJob = new SumJob(1, 2)
      sumJob.numberOfWorker = 1000
      sumJob.maxRetry = 3

      controller ! sumJob
    }

    controller.stop()
    time = System.currentTimeMillis() - time

    println("time = " + time)
  }

}