/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.model

import java.util
import org.springframework.data.annotation.Id

/**
 * The Class Linker.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:49 PM
 *
 */

class Link {

  @Id
  var id: String = _

  var url: String = _
  var content: Array[Byte] = _
  var responseCode: Int = _

  //Metadata
  var contentEncoding: String = _
  var html: String = _
  var text: String = _
  var title: String = _
  var description: String = _
  var featureImageUrl: String = _
  var language: String = _

  //Feature Image
  var featureImage: Array[Byte] = _

  var indexDate: util.Date = _

  override def equals(obj: Any) = {
    obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url
  }

  override def hashCode() = url.hashCode
}
