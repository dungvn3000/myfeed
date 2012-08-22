/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class JobStauts.
 *
 * @author Nguyen Duc Dung
 * @since 8/23/12, 5:58 AM
 *
 */
object JobStatus {
  //The code for the job has done by the handler.
  val DONE = 0

  //The code for the error job.
  val ERROR = 1

  //The code for the job has skipped by the handler.
  val SKIP = 2
}
