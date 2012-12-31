package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class FeedGroup.
 *
 * @author Nguyen Duc Dung
 * @since 12/30/12 5:04 PM
 *
 */
case class FeedGroup
(
  _id: ObjectId = new ObjectId,
  name: String

  ) {
  //Convenience method to convert _id to String.
  def id = _id.toString
}
