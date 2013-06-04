package vn.myfeed.core.actor

import akka.actor.ActorSystem

/**
 * The Class Akka.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 11:42 PM
 *
 */
object Akka {

  lazy val system = ActorSystem("systemActor")

}
