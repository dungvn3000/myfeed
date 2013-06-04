package vn.myfeed.model

import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject

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

object UserNews extends SalatDAO[UserNews, ObjectId](mongo("userNews")) {

  def getUserNews(userId: ObjectId) = find(MongoDBObject(
    "userId" -> userId
  )).toList

  def findByNews(newsId: String, userId: ObjectId) = findOne(MongoDBObject(
    "newsId" -> newsId,
    "userId" -> userId
  ))

  def getUserClicks(userId: ObjectId) = {
    val newsId = find(MongoDBObject(
      "userId" -> userId,
      "clicked" -> true
    )).toList.map(_.newsId)

    val news = News.find(MongoDBObject(
      "_id" -> MongoDBObject("$in" -> newsId)
    )).toList

    news
  }

}