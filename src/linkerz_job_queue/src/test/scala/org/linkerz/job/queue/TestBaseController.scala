/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import handler.SyncHandler
import job.SumJob
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import junit.framework.Assert

/**
 * The Class TestBaseController.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 3:27 AM
 *
 */
class TestBaseController extends AssertionsForJUnit {

  @Test
  def testWithSyncHandler() {
    val controller = new BaseController
    controller.handlers = List(new SyncHandler)
    controller.start()
    val sumJob = new SumJob(1, 2)
    controller ! sumJob
    controller.stop()

    Assert.assertEquals(sumJob.result.get, 3)
  }
}