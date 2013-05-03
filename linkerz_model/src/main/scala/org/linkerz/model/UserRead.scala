package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class Read.
 *
 * @author Nguyen Duc Dung
 * @since 5/2/13 9:31 AM
 *
 */
case class UserRead(
                     _id: ObjectId = new ObjectId,
                     feedId: ObjectId,
                     linkId: ObjectId,
                     click: Boolean = false,
                     createdDate: DateTime = DateTime.now
                     ) extends BaseModel(_id)
