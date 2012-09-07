/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.user

import org.linkerz.mongodb.model.{User, Link}

/**
 * The Class UserService.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:16 PM
 *
 */

trait UserService {

  def getUser(userName: String): User

  def userClick(userName: String, linkId: String, links: List[Link])
}
