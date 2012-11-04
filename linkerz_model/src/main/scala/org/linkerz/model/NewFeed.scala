/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import java.util
import org.springframework.data.annotation.Id

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

class NewFeed {

  @Id
  var id: String = _

  var name: String = _
  var group: String = _
  var url: String = _
  var time: Int = _
  var enable: Boolean = _

  var urlRegex: java.util.List[String] = _
  var excludeUrl: java.util.List[String] = _

  var indexTime: util.Date = _
}
