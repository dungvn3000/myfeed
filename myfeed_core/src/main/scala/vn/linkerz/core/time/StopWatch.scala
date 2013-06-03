package org.linkerz.core.time

import grizzled.slf4j.Logging

/**
 * The Class Timer.
 *
 * @author Nguyen Duc Dung
 * @since 11/9/12 6:39 PM
 *
 */
trait StopWatch extends Logging {

  def stopWatch(name: String)(f: => Unit) {
    var time = System.currentTimeMillis()
    info("Start watching - " + name)
    f
    time = System.currentTimeMillis() - time
    info(name + " - finished in " + time + " ms")
  }

}
