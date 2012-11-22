package org.linkerz

import akka.actor.{Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory
import org.linkerz.Monitor

/**
 * The Class Server.
 *
 * @author Nguyen Duc Dung
 * @since 11/21/12 2:26 PM
 *
 */
object Server extends App {

  val system = ActorSystem("serverSystem", ConfigFactory.load.getConfig("serverSystem"))
  val actor = system.actorOf(Props[Monitor], "server")

}


