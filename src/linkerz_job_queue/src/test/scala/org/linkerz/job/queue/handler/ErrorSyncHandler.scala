/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core.Job
import org.linkerz.job.queue.exception.TestException

/**
 * The Handler will throw exception when handle a job.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 5:44 AM
 *
 */
class ErrorSyncHandler extends SyncHandler {
  override def handle(job: Job) {
    throw new TestException
  }
}
