/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob


/**
 * The Class AutoParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:30 PM
 *
 */

class AutoDetectParser(plugins: List[CustomParser]) extends Parser {

  val defaultParser = new DefaultParser

  /**
   * The parser will automatic parser suitable for the website.
   * If can't not find it, the parser will using default parser.
   * @param crawlJob
   */
  def parse(crawlJob: CrawlJob) {
    plugins.foreach(plugin => {
      if (plugin.isMatch(crawlJob.webUrl.url)) {
        plugin.parse(crawlJob)
        return
      }
    })
    defaultParser.parse(crawlJob)
  }
}
