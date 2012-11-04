package org.linkerz.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Criteria._
import org.linkerz.model.mongodb.MongoOperation.mongo

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 6:05 PM
 *
 */

class User {
  @Id
  var id: String = _
  var userName: String = _
  var passWord: String = _
}

object UserDao {
  def findByUserName(userName: String) = {
    mongo.findOne(query(where("userName").is(userName)), classOf[User])
  }
}
