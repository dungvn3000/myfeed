/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

import java.util

/**
 * The Class Feeds.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 7:47 PM
 *
 */

class NewFeed extends LinkerZEntity {

  var name: String = _
  var group: String = _
  var url: String = _
  var time: Int = _
  var enable: Boolean = _

  var indexTime: util.Date = _
}