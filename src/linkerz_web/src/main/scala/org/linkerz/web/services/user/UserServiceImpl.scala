/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.user

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Criteria._
import org.linkerz.mongodb.model.{Link, User}
import java.util

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

  def getUserWebPages(userName: String): java.util.List[Link] = {
    val user = mongoOperations.findOne(query(where("userName").is(userName)), classOf[User])
    if (user != null) {
      return mongoOperations.find(query(where("userId").is(user.id)), classOf[Link])
    }
    util.Collections.emptyList()
  }
}
