package vn.myfeed.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import vn.myfeed.model.News
import org.joda.time.DateTime

/**
 * The Class LinkDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object NewsDao extends SalatDAO[News, String](mongo("news")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(news: News) = {
    val result = findOne(MongoDBObject(
      "$or" -> Array(
        MongoDBObject("url" -> news.url),
        MongoDBObject("title" -> news.title)
      )
    ))

    if (result.isEmpty) {
      save(news)
      Some(news)
    } else None
  }

  def getAfter(start: DateTime, feedIds: List[ObjectId]) = find(
    MongoDBObject(
      "createdDate" -> MongoDBObject("$gt" -> start),
      "feedId" -> MongoDBObject("$in" -> feedIds)
    )
  ).toList


  def getAfter(start: DateTime) = find(MongoDBObject("indexDate" -> MongoDBObject("$gt" -> start))).toList

}
