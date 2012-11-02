/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
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

  //The code for the new job.
  val NEW = "NEW"

  //The code for the job has done by the handler.
  val DONE = "DONE"

  //The code for the error job.
  val ERROR = "ERROR"

  //The code for the job has skipped by the handler.
  val SKIP = "SKIP"
}
