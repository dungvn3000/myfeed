package org.linkerz.dao

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import org.linkerz.model.News
import org.joda.time.DateTime

/**
 * The Class LinkDao.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 1:07 PM
 *
 */
object LinkDao extends SalatDAO[News, ObjectId](mongo("link")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: News) = {
    val result = findOne(MongoDBObject(
      "$or" -> Array(
        MongoDBObject("url" -> link.url),
        MongoDBObject("title" -> link.title)
      )
    ))

    if (result.isEmpty) {
      save(link)
      Some(link)
    } else None
  }

  def getAfter(start: DateTime, feedIds: List[ObjectId]) = {
    val links = find(
      MongoDBObject(
        "indexDate" -> MongoDBObject("$gt" -> start),
        "feedId" -> MongoDBObject("$in" -> feedIds)
      )
    ).toList
    links
  }

  def getAfter(start: DateTime) = {
    val links = find(MongoDBObject("indexDate" -> MongoDBObject("$gt" -> start))).toList
    links
  }
}
