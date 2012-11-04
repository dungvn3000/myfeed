/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO
import java.util.Date

/**
 * The Class Linker.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

case class Link(

   _id: ObjectId = new ObjectId,

   url: String,
   content: Array[Byte],
   responseCode: Int,

   //Metadata
   contentEncoding: String,
   title: String,
   description: String,
   featureImageUrl: String,

   //Feature Image
   featureImage: Array[Byte],

   indexDate: Date = new Date

) {
  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}

object Link extends SalatDAO[Link, ObjectId](collection = mongo("link")) {

}