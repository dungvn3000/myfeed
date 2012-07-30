/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.BaseController
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * The Class TestMultiHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 12:35 AM
 *
 */

@RunWith(classOf[JUnitRunner])
class TestMultiHandler extends FunSuite {

  test("testMultiHandler") {
    val controller = new BaseController

    val echoJob = new EchoJob("Hello Frist Task")
    val echoHandler = new EchoHandler

    val sumJob = new SumJob(1, 2)
    val sumHandler = new SumHandler

    val inSessionJob = new InSessionJob
    val handlerWithSession = new HandlerWithSession

    val jobHasSubJob = new JobHasSubJob
    val asyncHandler = new TestAsyncHandler
    for (i <- 1 to 10) {
      asyncHandler.workers += new TestWorker(i)
    }


    controller.handlers += echoHandler
    controller.handlers += sumHandler
    controller.handlers += handlerWithSession
    controller.handlers += asyncHandler

    //Add async job first to test.
    controller.add(jobHasSubJob)

    controller.add(echoJob)
    controller.add(sumJob)
    controller.add(inSessionJob)

    controller.start()

    Thread.sleep(10000)

    controller.stop()

    assert(echoJob.result().get == "DONE")
    assert(sumJob.result().get == 3)
    assert(inSessionJob.result().get != null)
    assert(!jobHasSubJob.result().isEmpty)
    assert(jobHasSubJob.result().get.size == 10)
  }

}
