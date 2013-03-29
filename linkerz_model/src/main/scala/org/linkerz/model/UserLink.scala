package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class UserLink.
 *
 * @author Nguyen Duc Dung
 * @since 3/29/13 4:35 PM
 *
 */
case class UserLink(_id: ObjectId = new ObjectId(),
                    userId: ObjectId,
                    linkId: ObjectId,
                    created: DateTime = DateTime.now()
                     ) extends BaseModel(_id)
