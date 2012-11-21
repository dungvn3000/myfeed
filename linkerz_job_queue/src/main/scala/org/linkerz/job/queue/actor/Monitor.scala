package org.linkerz.job.queue.actor

import akka.actor.Actor
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
  }
}
