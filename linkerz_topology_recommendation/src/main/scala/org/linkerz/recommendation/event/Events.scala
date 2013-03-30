package org.linkerz.recommendation.event

import org.linkerz.model.{UserLink, Link}
import org.bson.types.ObjectId

/**
 * The Class Events.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 11:50 AM
 *
 */
sealed trait Event extends Serializable

object Recommendation extends Event
case class GetUserLink(userLinks: List[UserLink]) extends Event
case class MergeLink(userLink: UserLink, link: Link) extends Event
case class Correlation(clickedLinkId: ObjectId, linkId: ObjectId, score: Double) extends Event

