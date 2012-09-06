/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.example.weka

import org.linkerz.weka.mongodb.annotation.Attribute
import org.linkerz.weka.mongodb.annotation.Attribute.TYPE._

/**
 * The Class UserLike.
 *
 * @author Nguyen Duc Dung
 * @since 9/6/12 2:10 PM
 *
 */
class UserLike {

  @Attribute(name = "like", attType = NOMINAL, values = Array("yes", "no"))
  var like: String = _

  @Attribute(name = "title", attType = STRING)
  var title: String = _

}
