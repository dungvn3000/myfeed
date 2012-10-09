/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.web.services.newfeed

import org.linkerz.weka.mongodb.annotation.Attribute
import org.linkerz.weka.mongodb.annotation.Attribute.TYPE._

/**
 * The Class UserLike.
 *
 * @author Nguyen Duc Dung
 * @since 9/8/12 5:59 AM
 *
 */
class UserLike {

  @Attribute(name = "like", attType = NOMINAL, values = Array("yes", "no"))
  var like: String = _

  @Attribute(name = "title", attType = STRING)
  var title: String = _

}
