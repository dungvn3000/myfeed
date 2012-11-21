package org.linkerz.job.queue.event

/**
 * The Class RemoteEvents.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 12:10 AM
 *
 */
object RemoteEvents {

  sealed trait RemoteEvents

  case class Login(msg: String) extends RemoteEvents

  case class Logout(msg: String) extends RemoteEvents

  case class Error(msg: String) extends RemoteEvents

  case class Start(msg: String) extends RemoteEvents

  case class Stop(msg: String) extends RemoteEvents

  case class Restart(msg: String) extends RemoteEvents

}
