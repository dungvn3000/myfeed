/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import java.util
import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

case class NewFeed
(
  _id: ObjectId = new ObjectId,

  name: String,
  group: String,
  url: String,
  enable: Boolean,

  urlRegex: String,
  excludeUrl: List[String] = Nil,

  titleSelection: String,
  contentSelection: String,
  imgSelection: String,

  urlTests: List[String] = Nil,
  titles: List[String] = Nil,
  validateTexts: List[String] = Nil,
  imageUrls: List[String] = Nil

  ) {

  def id = _id.toString

}

object NewFeedDao extends SalatDAO[NewFeed, ObjectId](collection = mongo("newfeed")) {

}