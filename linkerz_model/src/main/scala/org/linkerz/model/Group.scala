package org.linkerz.model

import org.bson.types.ObjectId

/**
 * The Class Group.
 *
 * @author Nguyen Duc Dung
 * @since 3/29/13 12:37 PM
 *
 */
case class Group(
                  _id: ObjectId = new ObjectId(),
                  userId: ObjectId,
                  name: String
                  ) extends BaseModel(_id)