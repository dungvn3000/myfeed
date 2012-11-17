/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO
import java.util.Date
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class Linker.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

case class Link
(
  _id: ObjectId = new ObjectId,

  url: String,
  content: Array[Byte],
  responseCode: Int,

  //Metadata
  text: Option[String] = None,
  contentEncoding: String,
  title: String,
  //  description: Option[String] = None,
  featureImageUrl: Option[String] = None,
  //Feature Image
  featureImage: Option[Array[Byte]] = None,

  parsed: Boolean = false,

  indexDate: Date = new Date

  ) {
  //Convenience method to convert _id to String.
  def id = _id.toString

  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}

object LinkDao extends SalatDAO[Link, ObjectId](collection = mongo("link")) {

  def findByUrl(url: String) = findOne(MongoDBObject("url" -> url))

  def checkAndSave(link: Link) = {
    val result = findOne(MongoDBObject("url" -> link.url))
    if (result.isEmpty) {
      save(link)
      true
    }
    false
  }
}