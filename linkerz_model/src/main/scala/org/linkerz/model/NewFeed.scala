/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId

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

  group: String,
  url: String,
  enable: Boolean,

  urlRegex: String,
  excludeUrl: List[String] = Nil,

  contentSelection: String
  ) {

  def id = _id.toString

}

