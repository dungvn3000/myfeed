package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import org.linkerz.model.UserNews
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class UserFeedDao.
 *
 * @author Nguyen Duc Dung
 * @since 5/16/13 4:00 PM
 *
 */
object UserNewsDao extends SalatDAO[UserNews, ObjectId](mongo("userNews")) {

  def getUserNews(userId: ObjectId) = find(MongoDBObject(
    "userId" -> userId
  )).toList

  def findByNews(newsId: String) = findOne(MongoDBObject(
    "newsId" -> newsId
  ))

  def getUserClicked(userId: ObjectId) = {
    val newsId = find(MongoDBObject(
      "userId" -> userId,
      "clicked" -> true
    )).toList.map(_.newsId)

    val news = NewsDao.find(MongoDBObject(
      "_id" -> MongoDBObject("$in" -> newsId)
    )).toList

    news
  }

}
