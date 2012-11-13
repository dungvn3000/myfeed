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
  id: ObjectId = new ObjectId,

  name: String,
  group: String,
  url: String,
  enable: Boolean,

  urlRegex: java.util.List[String] = Collections.emptyList(),
  excludeUrl: java.util.List[String] = Collections.emptyList()
  )

object NewFeed extends SalatDAO[NewFeed, ObjectId](collection = mongo("newfeed")) {

}