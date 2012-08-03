/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.mongodb.model

import org.springframework.data.annotation.Id

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:05 PM
 *
 */

class User extends LinkerZEntity {

  var userName: String = _
  var passWord: String = _

}
