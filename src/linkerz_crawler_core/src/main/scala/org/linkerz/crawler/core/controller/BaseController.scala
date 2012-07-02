package org.linkerz.crawler.core.controller

import java.util.{Map => JavaMap}
import org.linkerz.crawler.core.download.Downloader
import org.linkerz.crawler.core.parser.Parser
import reflect.BeanProperty
import org.linkerz.crawler.core.queue.CrawlQueue
import org.linkerz.crawler.core.job.CrawlJob

import collection.JavaConversions._
import org.linkerz.crawler.core.model.WebLink

/**
 * The Class BaseController.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:59 PM
 *
 */

class BaseController extends Controller {

  @BeanProperty
  var downloaders : JavaMap[String, Downloader] =_

  @BeanProperty
  var parsers : JavaMap[String, Parser] =_

  val queue = new CrawlQueue

  /**
   * Start form this url.
   * @param url
   */
  def start(url: String) {
    val job = new CrawlJob(downloaders.toMap, parsers.toMap, new WebLink(url))
    queue.add(job)
    queue.run()
  }
}
