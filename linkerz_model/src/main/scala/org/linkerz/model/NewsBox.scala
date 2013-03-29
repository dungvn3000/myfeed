package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class NewsBox.
 *
 * @author Nguyen Duc Dung
 * @since 11/11/12 2:25 AM
 *
 */
case class NewsBox(
                    _id: ObjectId = new ObjectId(),
                    userId: ObjectId,
                    linkId: ObjectId,
                    group: String,
                    click: Boolean = false,
                    createdDate: DateTime = DateTime.now()
                    ) extends BaseModel(_id)