package org.linkerz.recommendation.event

import org.linkerz.model.News
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
case class GetUserLink(userLinks: List[News]) extends Event
case class MergeLink(userLink: News, link: News) extends Event
case class Correlation(clickedLinkId: ObjectId, linkId: ObjectId, score: Double) extends Event

