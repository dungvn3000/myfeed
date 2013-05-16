package org.linkerz.delivery.event

import org.linkerz.model.News

/**
 * The Class Events.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 11:50 AM
 *
 */
sealed trait Event extends Serializable

object Start extends Event
case class GetNewsDone(news: News, userClicked: List[News]) extends Event

