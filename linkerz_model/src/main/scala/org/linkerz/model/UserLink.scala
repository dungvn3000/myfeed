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
                    url: String,
                    //Metadata
                    title: String,
                    description: Option[String] = None,
                    text: Option[String] = None,
                    contentEncoding: String = "UTF-8",
                    featureImage: Option[ObjectId] = None,
                    created: DateTime = DateTime.now()
                     ) extends BaseModel(_id)
