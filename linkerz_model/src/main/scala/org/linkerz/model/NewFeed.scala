/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12 7:47 PM
 *
 */

class NewFeed {
  @Id
  var id: String = _
  var name: String = _
  var group: String = _
  var url: String = _
  var enable: Boolean = _
  var urlRegex: List[String] = Nil
  var excludeUrl: List[String] = Nil
  var titleSelection: String = _
  var titleAttName: String = _
  var titleMaxLength: Int = _
  var contentSelection: String = _
  var imgSelection: String = _

  var urlTest1: Option[String] = None
  var title1: Option[String] = None
  var validateText1: Option[String] = None
  var imageUrl1: Option[String] = None

  var urlTest2: Option[String] = None
  var title2: Option[String] = None
  var validateText2: Option[String] = None
  var imageUrl2: Option[String] = None

  var urlTest3: Option[String] = None
  var title3: Option[String] = None
  var validateText3: Option[String] = None
  var imageUrl3: Option[String] = None
}