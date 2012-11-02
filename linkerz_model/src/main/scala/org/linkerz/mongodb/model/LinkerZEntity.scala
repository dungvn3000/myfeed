/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.mongodb.model

import org.springframework.data.annotation.Id

/**
 * The Class LinkerZEntity.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:50 PM
 *
 */

trait LinkerZEntity {

  @Id
  var id: String = _

}
