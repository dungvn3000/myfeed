/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.client

import com.hazelcast.client.{ClientConfig, HazelcastClient}
import org.linkerz.crawler.core.job.DistributedCrawlJob

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/30/12, 9:54 PM
 *
 */

object Main extends App {

  val clientCfg = new ClientConfig
  clientCfg.getGroupConfig.setName("dev").setPassword("dev")
  clientCfg.addAddress("localhost")
  val client = HazelcastClient.newHazelcastClient(clientCfg)
  client.getQueue("jobQueue").put(new DistributedCrawlJob("http://vnexpress.net/"))
  client.getQueue("jobQueue").put(new DistributedCrawlJob("http://vnexpress.net/"))
  client.shutdown()
}
