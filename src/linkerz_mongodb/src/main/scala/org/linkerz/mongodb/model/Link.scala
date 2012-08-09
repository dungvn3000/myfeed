/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

import java.util

/**
 * The Class Linker.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

class Link extends LinkerZEntity {

  var url: String = _
  var content: Array[Byte] = _

  //Link to user
  var userId: String = _

  //Metadata
  var contentEncoding: String = _
  var html: String = _
  var text: String = _
  var title: String = _
  var subTitle: String = _
  var featureImageUrl: String = _
  var language: String = _

  var indexDate: util.Date = _

}
