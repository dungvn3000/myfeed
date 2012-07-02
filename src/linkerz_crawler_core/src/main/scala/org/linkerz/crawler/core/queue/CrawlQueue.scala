package org.linkerz.crawler.core.queue

import org.linkerz.crawler.core.job.Job
import collection.mutable.ListBuffer

/**
 * The Class CrawlQueue.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 10:47 PM
 *
 */

class CrawlQueue extends Queue {

  var jobs = new ListBuffer[Job]

  def add(job: Job) {
    jobs += job
  }

  def run() {
    jobs.foreach(job => {
      job.execute()
    })
  }
}
