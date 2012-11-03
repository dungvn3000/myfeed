/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.mongodb

import org.bson.types.ObjectId
import com.novus.salat.annotations.raw.Key


/**
 * The Class LinkerZEntity.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:50 PM
 *
 */

trait LinkerZEntity {

  @Key("_id")
  var id: ObjectId = _

}
