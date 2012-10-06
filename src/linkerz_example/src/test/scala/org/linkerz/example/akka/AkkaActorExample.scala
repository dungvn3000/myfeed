/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.example.akka

import akka.actor.{Actor, Props, ActorSystem}
import akka.event.Logging

/**
 * The Class AkkaActorExample.
 *
 * @author Nguyen Duc Dung
 * @since 10/6/12 2:53 AM
 *
 */

object AkkaActorExample extends App {
  val system = ActorSystem("MySystem")
  val manager = system.actorOf(Props[Manager], name = "manager")

  for (i <- 1 to 100000) {
    manager ! i
  }
}

class Manager extends Actor {
  val worker = context.system.actorOf(Props[Worker], name = "worker")
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
      sender ! "done"
    }
  }
}
