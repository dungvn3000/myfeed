/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package vn.myfeed.model

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

case class Feed(
                 _id: ObjectId = new ObjectId,
                 name: String,
                 url: String,
                 topic: Option[String] = None
                 ) extends BaseModel(_id)

object Feed extends SalatDAO[Feed, ObjectId](mongo("feed")) {

  def all = find(MongoDBObject.empty).toList

}