/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import core.JobStatus
import exception.TestException
import handler.{ErrorSyncHandler, SyncHandler}
import job.SumJob
import org.junit.Test
import junit.framework.Assert

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
    val sumJob1 = new SumJob(1, 2)
    val sumJob2 = new SumJob(3, 4)
    controller ! sumJob1
    controller ! sumJob2
    controller.stop()

    Assert.assertEquals(sumJob1.result.get, 3)
    Assert.assertEquals(sumJob2.result.get, 7)
  }

  @Test
  def testWithErrorSyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new ErrorSyncHandler)
    controller.start()
    val sumJob1 = new SumJob(1, 2)
    controller ! sumJob1
    controller.stop()

    Assert.assertEquals(sumJob1.status, JobStatus.ERROR)
    Assert.assertEquals(sumJob1.error.head._2.getClass, classOf[TestException])
  }

}