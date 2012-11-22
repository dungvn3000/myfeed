package org.linkerz

import akka.actor.Actor
import crawler.core.job.CrawlJob
import org.linkerz.job.queue.event.RemoteEvents._
import org.linkerz.job.queue.event.RemoteEvents.{LoginOk, Login}

/**
 * This actor is run in server to monitor controllers.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 12:01 AM
 *
 */
class Monitor extends Actor {
  protected def receive = {
    case Login(msg) => {
      println(sender.path.address.host)
      sender ! LoginOk("Robot 1")
    }
    case Processing(job) => job match {
      case job: CrawlJob => println(job.webUrl.url)
    }
  }
}
