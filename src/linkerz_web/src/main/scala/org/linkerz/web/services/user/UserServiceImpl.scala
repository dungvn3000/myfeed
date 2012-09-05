/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.user

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Criteria._
import org.linkerz.mongodb.model.{UserClick, User}

/**
 * The Class UserServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:17 PM
 *
 */

class UserServiceImpl extends UserService {

  @BeanProperty
  var mongoOperations: MongoOperations = _

  def getUser(userName: String) = {
    mongoOperations.findOne(query(where("userName").is(userName)), classOf[User])
  }

  def userClick(userName: String, linkId: String) {
    val userClick = new UserClick
    userClick.userName = userName
    userClick.linkId = linkId

    mongoOperations.save(userClick)
  }
}
