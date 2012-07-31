/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.client

import com.hazelcast.client.{HazelcastClient, ClientConfig}
import org.linkerz.crawler.core.job.DistributedCrawlJob

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 7/31/12, 7:00 PM
 *
 */

class Main {
  val clientCfg = new ClientConfig
  clientCfg.getGroupConfig.setName("dev").setPassword("dev")
  clientCfg.addAddress("localhost")
  val client = HazelcastClient.newHazelcastClient(clientCfg)
  client.getQueue("jobQueue").put(new DistributedCrawlJob("http://vnexpress.net/"))
  client.getQueue("jobQueue").put(new DistributedCrawlJob("http://vnexpress.net/"))
  client.shutdown()
}
