package org.linkerz.job.queue.event

import org.linkerz.job.queue.core.{Session, Job}
import akka.actor.ActorRef

/**
 * The Class Events.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 12:05 AM
 *
 */
object Events {

  sealed trait Event

  case class StartWatching[J <: Job](job: J, manager: ActorRef) extends Event

  case class Next[J <: Job, S <: Session[J]](job: J, session: S) extends Event

  case class Progress(percent: Int) extends Event

  case class Success[J <: Job](job: J) extends Event

  case class Fail[J <: Job](job: J, ex: Exception) extends Event

}
