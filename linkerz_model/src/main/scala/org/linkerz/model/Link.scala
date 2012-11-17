/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import java.util.Date
import org.linkerz.dao.MongoTemplate
import MongoTemplate._
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Criteria._

/**
 * The Class Linker.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

case class Link {

  var id: String = _

  var url: String = _
  var content: Array[Byte] = _
  var responseCode: Int = _

  //Metadata
  var text: Option[String] = None
  var contentEncoding: String = _
  var title: String = _

  //  description: Option[String] = None,
  var featureImageUrl: Option[String] = None
  //Feature Image
  var featureImage: Option[Array[Byte]] = None

  var parsed: Boolean = false

  var indexDate: Date = new Date


  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}