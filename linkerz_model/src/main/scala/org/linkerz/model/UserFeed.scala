package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class UserFeed.
 *
 * @author Nguyen Duc Dung
 * @since 5/16/13 3:58 PM
 *
 */
case class UserFeed(
                     _id: ObjectId = new ObjectId(),
                     feedId: ObjectId,
                     createdDate: DateTime = DateTime.now
                     ) extends BaseModel(_id)
