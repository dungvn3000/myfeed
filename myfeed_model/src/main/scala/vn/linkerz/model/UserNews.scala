package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class UserNews.
 *
 * @author Nguyen Duc Dung
 * @since 5/16/13 3:43 PM
 *
 */
case class UserNews(
                     _id: ObjectId = new ObjectId,
                     userId: ObjectId,
                     feedId: ObjectId,
                     newsId: String,
                     score: Double = 0,
                     recommend: Boolean = false,
                     read: Boolean = false,
                     clicked: Boolean = false,
                     createdDate: DateTime = DateTime.now
                     ) extends BaseModel(_id)