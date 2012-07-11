/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.example.scalaz

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scalaz.example.concurrent.PingPong

/**
 * The Class PingPongExample.
 *
 * @author Nguyen Duc Dung
 * @since 7/11/12, 4:43 PM
 *
 */
@RunWith(classOf[JUnitRunner])
class PingPongExample extends FunSuite {

  test("testPingPong") {
    PingPong.run
  }

}
