/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import java.util.Date

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
                 responseCode: Int,

                 //Metadata
                 text: Option[String] = None,
                 description: Option[String] = None,
                 contentEncoding: String,
                 title: String,
                 //Feature Image
                 featureImage: Option[Array[Byte]] = None,

                 indexDate: Date = new Date

                 ) extends LinkerZModel(_id) {

  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}

