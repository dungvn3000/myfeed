package org.linkerz.crawl.topology.actor

import org.linkerz.logger.DBLogger
import akka.actor.Actor
import org.linkerz.dao.NewFeedDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.model.{LogCategory, NewFeed}
import org.linkerz.crawl.topology.parser.CustomParser
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawl.topology.downloader.DefaultDownload
import org.linkerz.crawl.topology.job.CrawlJob
import org.apache.commons.lang.StringUtils
import java.util.UUID
import org.linkerz.crawl.topology.event.Start
import grizzled.slf4j.Logging
import backtype.storm.tuple.Values
import backtype.storm.spout.SpoutOutputCollector

/**
 * The Class ScheduleActor.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 10:19 PM
 *
 */
class ScheduleActor(collector: SpoutOutputCollector) extends Actor with DBLogger with Logging {

  protected def receive = {
    case "run" => {
      val newFeeds = NewFeedDao.find(MongoDBObject("enable" -> true)).toList
      newFeeds.foreach(feed => {
        info("Start testing " + feed.url)
        if (runTestCase(feed)) {
          val job = new CrawlJob(feed)
          job.maxDepth = 1 // Set level is 2 because we will get related link.
          job.politenessDelay = 1000
          info("Start Crawling " + feed.url)
          //Make sure the id is unique all the time.
          val sessionId = UUID.randomUUID()
          collector.emit(new Values(sessionId, Start(job)), sessionId)
        }
      })
    }
  }

  def runTestCase(feed: NewFeed) = {
    val parser = CustomParser(feed)
    var error = false

    val cf = new AsyncHttpClientConfig.Builder()
      .setFollowRedirects(true)
      .setMaximumNumberOfRedirects(10)
      .build()

    val downloader = new DefaultDownload(new AsyncHttpClient(cf))

    try {
      for (i <- 0 to feed.urlTests.size - 1) {
        val crawlJob = new CrawlJob(feed.urlTests(i))
        downloader.download(crawlJob)
        parser.parse(crawlJob)

        crawlJob.result map {
          webPage => if (StringUtils.isBlank(webPage.title)) {
            storeError("Can't parse the title", url = webPage.webUrl.url, category = LogCategory.TestCase)
            error = true
          }

          if (StringUtils.isNotBlank(webPage.title) && webPage.title != feed.titles(i)) {
            storeError("The title do not match with the test case", url = webPage.webUrl.url, category = LogCategory.TestCase)
            error = true
          }

          webPage.text map {
            text => if (!text.contains(feed.validateTexts(i))) {
              storeError("The text do not match with the test case", url = webPage.webUrl.url, category = LogCategory.TestCase)
              error = true
            }
          } getOrElse {
            storeError("Can't parse the text content", url = webPage.webUrl.url, category = LogCategory.TestCase)
            error = true
          }

          webPage.featureImageUrl map {
            url => if (url != feed.imageUrls(i)) {
              storeError("The image url do not match with the test case", url = webPage.webUrl.url, category = LogCategory.TestCase)
              error = true
            }
          } getOrElse {
            storeError("Can't parse the image url", url = webPage.webUrl.url, category = LogCategory.TestCase)
            error = true
          }

        } getOrElse {
          storeError("Download error", url = feed.urlTests(i), category = LogCategory.TestCase)
          error = true
        }
      }
    }
    catch {
      case ex: Exception => {
        collector.reportError(ex)
        storeError(ex.getMessage, exception = ex, url = feed.url, category = LogCategory.TestCase)
        error = true
      }
    }
    //Close connection.
    downloader.close()
    !error
  }
}
