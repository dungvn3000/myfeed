/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package vn.myfeed.model

import org.joda.time.DateTime
import com.novus.salat.dao.SalatDAO
import scala.Array
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import scala.Some

/**
 * The Class Link.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

case class News(
                 //this is a url after normalization.
                 _id: String,
                 feedId: ObjectId,
                 url: String,
                 //Metadata
                 title: String,
                 description: Option[String] = None,
                 text: Option[String] = None,
                 score: Double = 0d,

                 createdDate: DateTime = DateTime.now()
                 ) extends BaseModel(_id) {

  override def equals(obj: Any) = {
    obj.isInstanceOf[News] && obj.asInstanceOf[News].url == url
  }

  override def hashCode() = url.hashCode
}

object News extends SalatDAO[News, String](mongo("news")) {

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
