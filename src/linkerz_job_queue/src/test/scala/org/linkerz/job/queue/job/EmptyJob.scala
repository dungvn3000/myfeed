/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.job

import org.linkerz.job.queue.core.Job

/**
 * The Class EmptyJob.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 1:07 PM
 *
 */
class EmptyJob extends Job {

  private var _parent: Option[EmptyJob] = None

  var count: Int = 0

  def this(parent: EmptyJob) {
    this
    _parent = Some(parent)
  }

  override def parent = _parent

  def result = Some(count)

}
