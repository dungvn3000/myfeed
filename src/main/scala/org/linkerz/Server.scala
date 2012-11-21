package org.linkerz

import akka.actor.{Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

/**
 * The Class Server.
 *
 * @author Nguyen Duc Dung
 * @since 11/21/12 2:26 PM
 *
 */
object Server extends App {

  val system = ActorSystem("serverSystem", ConfigFactory.load.getConfig("serverSystem"))
  val actor = system.actorOf(Props[ServerActor], "server")

}

class ServerActor extends Actor {
  protected def receive = {
    case name: String => println(name)
  }
}
