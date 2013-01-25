package org.linkerz.topology.delivery.event

import org.linkerz.model.Link

/**
 * The Class Events.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 1:14 AM
 *
 */

sealed trait Event extends Serializable

case object Start extends Event

case object Delivery extends Event

case class GetNews(links: List[Link]) extends Event