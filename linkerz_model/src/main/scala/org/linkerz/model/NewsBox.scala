package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date

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
                    groupName: String,
                    click: Boolean = false,
                    createdDate: Date = new Date()
                    ) extends LinkerZModel(_id)