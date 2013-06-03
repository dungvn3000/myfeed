package vn.myfeed.model

import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject

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
                     userId: ObjectId,
                     //This is not name of feed, it is a name form user.
                     name: String,
                     createdDate: DateTime = DateTime.now
                     ) extends BaseModel(_id)

object UserFeed extends SalatDAO[UserFeed, ObjectId](mongo("userFeed")) {

  def getUserFeed(userId: ObjectId) = find(MongoDBObject(
    "userId" -> userId
  )).toList

  def findFeed(userId: ObjectId, feedId: ObjectId) = findOne(MongoDBObject(
    "userId" -> userId,
    "feedId" -> feedId
  ))

}