package org.linkerz.recommendation.event

import org.linkerz.model.Link
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
case class GetClickedLink(clickedLink: Link) extends Event
case class MergeLink(clickedLink: Link, link: Link) extends Event
case class Correlation(clickedLinkId: ObjectId, linkId: ObjectId, score: Double) extends Event

