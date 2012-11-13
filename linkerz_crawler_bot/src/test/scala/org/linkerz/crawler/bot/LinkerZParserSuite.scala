/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot

import factory.NewsParserFactory
import org.linkerz.crawler.core.fetcher.DefaultFetcher
import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob
import org.scalatest.FunSuite

/**
 * The Class TestLinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:34 PM
 *
 */
class LinkerZParserSuite extends FunSuite {

  test("test linkerz parser") {
    val fetcher = new DefaultFetcher(new DefaultDownloadFactory, new NewsParserFactory)
    val crawlJob1 = new CrawlJob("http://vnexpress.net/gl/phap-luat/2012/08/dai-gia-dat-cang-bi-dieu-tra-lua-dao-1-000-ty-dong/")
    fetcher.fetch(crawlJob1)
    val webPage1 = crawlJob1.result.get
    println("link = " + webPage1.webUrl.url)
    println("title = " + webPage1.title)
    println("text = " + webPage1.text)
    println("img = " + webPage1.featureImageUrl)


    val crawlJob2 = new CrawlJob("http://genk.vn/dien-thoai/cam-nhan-ipad-mini-tai-vn-may-dep-man-hinh-xau-20121103063935579.chn")
    fetcher.fetch(crawlJob2)
    val webPage2 = crawlJob2.result.get
    println("link = " + webPage2.webUrl.url)
    println("title = " + webPage2.title)
    println("text = " + webPage2.text)
    println("img = " + webPage2.featureImageUrl)
  }


}
