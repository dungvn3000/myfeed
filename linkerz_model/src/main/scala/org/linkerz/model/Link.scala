/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class Link.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

case class Link(
                 _id: ObjectId = new ObjectId,
                 feedId: ObjectId,

                 url: String,

                 //Metadata
                 text: Option[String] = None,
                 description: Option[String] = None,
                 contentEncoding: String = "UTF-8",
                 title: String,
                 score: Double = 0d,
                 //Feature Image
                 featureImage: Option[ObjectId] = None,

                 indexDate: DateTime = DateTime.now()

                 ) extends BaseModel(_id) {

  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}

