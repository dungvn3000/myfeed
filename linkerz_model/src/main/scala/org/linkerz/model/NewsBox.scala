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
                    groupId: ObjectId,

                    //Denormalization for avoiding join to link.
                    url: String,
                    title: String,
                    description: Option[String] = None,
                    featureImage: Option[ObjectId] = None,

                    read: Boolean = false,
                    click: Boolean = false,
                    createdDate: DateTime = DateTime.now()
                    ) extends BaseModel(_id)