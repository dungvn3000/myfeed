/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.example.scalaz

import java.util.concurrent.Executors._
import scalaz.concurrent.{Actor, Strategy}
import scalaz.Scalaz._
import org.junit.Test

/**
 * The Class HammerTimeExample.
 *
 * @author Nguyen Duc Dung
 * @since 7/12/12, 12:18 AM
 *
 */
class HammerTimeExample {

  @Test
  def testHammerTime() {
    run()
    Thread.sleep(10000)
  }

  def run() {
    implicit val pool = newCachedThreadPool
    implicit val strategy = Strategy.Executor

    val done = actor((u: Unit) => {
      println("TEST SUCCEEDED")
      pool.shutdown()
    })

    val fail = (e: Throwable) => {
      e.printStackTrace()
      pool.shutdown()
    }

    def hammer(other: => Actor[Int]) = actor(((i: Int) =>
      if (i == 0) done !()
      else other ! (i - 1)
      ), fail)

    lazy val hammer1: Actor[Int] = hammer(hammer(hammer1))
    hammer1 ! 1000000
  }
}
