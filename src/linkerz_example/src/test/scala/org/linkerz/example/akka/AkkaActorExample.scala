/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.example.akka

import akka.actor.{Actor, Props, ActorSystem}
import akka.event.Logging
import akka.routing.RoundRobinRouter

/**
 * The Class AkkaActorExample.
 *
 * @author Nguyen Duc Dung
 * @since 10/6/12 2:53 AM
 *
 */

object AkkaActorExample extends App {
  val system = ActorSystem("mySystem")
  val manager = system.actorOf(Props[Manager], name = "manager")

  for (i <- 1 to 10000) {
    manager ! i
  }
}

class Manager extends Actor {
  val worker = context.actorOf(Props[Worker].withRouter(RoundRobinRouter(1000)), name = "worker")
  val log = Logging(context.system, this)
  var done = 0
  var count = 0

  protected def receive = {
    case msg: Int => {
      count += 1
      worker ! msg
    }
    case "done" => {
      done += 1
      if (done == count) {
        log.info("done " + done + " jobs")
        log.info(context.system.uptime + " seconds")
        context.system.shutdown()
      }
    }
  }
}

class Worker extends Actor {
  val log = Logging(context.system, this)

  protected def receive = {
    case msg: Int => {
      log.info("sleep")
      Thread.sleep(1000)
      sender ! "done"
    }
  }
}
