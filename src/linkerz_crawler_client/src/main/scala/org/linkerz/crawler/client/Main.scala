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

//  client.getTopic("event").publish(new CrawlEvent("http://vnexpress.net/"))

//  val task = new DistributedTask[String](new CrawlTask)
//  task.setExecutionCallback(new ExecutionCallback[String] {
//    def done(future: Future[String]) {
//      println("call back")
//    }
//  })

//  client.getExecutorService.execute(task)
//
//  var time = System.currentTimeMillis()
//
//  val result1 = client.getExecutorService.submit(new CrawlTask("1"))
//  val result2 = client.getExecutorService.submit(new CrawlTask("2"))
//  val result3= client.getExecutorService.submit(new CrawlTask("3"))
//
//  result1.get()
//  println("finish 1")
//  result2.get()
//  println("finish 2")
//  result3.get()
//  println("finish 3")
//
//  time = System.currentTimeMillis() - time
//
//  println(time)

  client.getQueue("jobQueue").put(new DistributedCrawlJob("http://vnexpress.net/"))

  client.shutdown()
}
