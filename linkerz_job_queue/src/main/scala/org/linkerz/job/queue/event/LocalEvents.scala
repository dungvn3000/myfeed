package org.linkerz.job.queue.event

import org.linkerz.job.queue.core.{Session, Job}
import akka.actor.ActorRef

/**
 * The Class LocalEvents.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 12:05 AM
 *
 */
object LocalEvents {

  sealed trait LocalEvent

  case class StartWatching[J <: Job](job: J, manager: ActorRef) extends LocalEvent

  case class Next[J <: Job, S <: Session[J]](job: J, session: S) extends LocalEvent

  case class Progress(percent: Int) extends LocalEvent

  case class Success[J <: Job](job: J) extends LocalEvent

  case class Fail[J <: Job](job: J, ex: Exception) extends LocalEvent

}
