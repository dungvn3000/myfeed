/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.example.scalaz

import java.util.concurrent.Executors
import scalaz.concurrent.Strategy
import scalaz.Scalaz._
import scalaz.concurrent.Actor
import org.junit.Test

/**
 * The Class PingPongExample.
 *
 * This example took from PingPong scalaz example.
 *
 * @author Nguyen Duc Dung
 * @since 7/11/12, 4:43 PM
 *
 */
class PingPongExample {

  val actors = 10000
  val pings = 3
  //The thread pool for actor.
  implicit val pool = Executors.newFixedThreadPool(3)
  implicit val s = Strategy.Executor

  class Pong {
    val p: Actor[Ping] = actor {
      (m: Ping) => m.p ! Pong.this
    }
  }

  class Ping(pings: Int, pong: Pong, cb: Actor[Ping]) {
    var n = pings
    val p: Actor[Pong] = actor {
      (m: Pong) =>
        n = n - 1
        if (n > 0) pong.p ! Ping.this else cb ! Ping.this
    }

    def start() {
      pong.p ! this
    }
  }

  @volatile var done = 0
  val callback = actor {
    (p: Ping) =>
      done = done + 1
      if (done % (actors / 10) == 0)
        println(done + " actors done -- " + pings * done + " total pongs.")
      else ()
      if (done >= actors) {
        println("All done.")
        pool.shutdown()
      } else ()
  }

  @Test
  def testPingPong() {
    (1 to actors).foreach {
      i =>
        val pong = new Pong
        (new Ping(pings, pong, callback)).start()
        if (i % (actors / 10) == 0)
          println(i + " actors started.")
        else ()
    }

    Thread.sleep(10000)
  }

}
