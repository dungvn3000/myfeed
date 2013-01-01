package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class UserFollow.
 *
 * @author Nguyen Duc Dung
 * @since 1/1/13 1:51 PM
 *
 */
case class UserFollow
(
  _id: ObjectId = new ObjectId,
  groupId: ObjectId,
  feedIds: List[ObjectId]
  )
