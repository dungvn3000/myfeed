package org.linkerz.recommendation.event

import org.linkerz.model.{Link, User}

/**
 * The Class Events.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 11:50 AM
 *
 */
sealed trait Event extends Serializable

/**
 * This event will starting the recommendation.
 */
object Start extends Event

case class GetUser(user: User) extends Event
case class GetLink(link: Link) extends Event

