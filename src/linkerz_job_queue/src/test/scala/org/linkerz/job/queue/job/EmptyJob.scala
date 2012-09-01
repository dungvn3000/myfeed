/*
 * Copyright (C) 2012 - 2013 LinkerZ
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

  var count: Int = 0

  def result = Some(count)
}
