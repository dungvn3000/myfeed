/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import java.util
import org.bson.types.ObjectId
import com.novus.salat.dao.SalatDAO
import util.Collections

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

  urlRegex: List[String] = Nil,
  excludeUrl: List[String] = Nil,

  titleSelection: String,
  titleAttName: String,
  titleMaxLength: Int,

  contentSelection: String,

  imgSelection: String

//  urlTest1: Option[String] = None,
//  title1: Option[String] = None,
//  validateText1: Option[String] = None,
//  imageUrl1: Option[String] = None,
//
//  urlTest2: Option[String] = None,
//  title2: Option[String] = None,
//  validateText2: Option[String] = None,
//  imageUrl2: Option[String] = None,
//
//  urlTest3: Option[String] = None,
//  title3: Option[String] = None,
//  validateText3: Option[String] = None,
//  imageUrl3: Option[String] = None

  ) {

  def id = _id.toString

}

object NewFeedDao extends SalatDAO[NewFeed, ObjectId](collection = mongo("newfeed")) {

}