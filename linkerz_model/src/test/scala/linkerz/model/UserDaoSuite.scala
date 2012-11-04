/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.model

import org.scalatest.FunSuite
import org.linkerz.model.{User, UserDao}
import org.linkerz.model.mongodb.MongoOperation.mongo

/**
 * The Class UserDaoSuite.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class UserDaoSuite extends FunSuite {

  test("test find user by user name") {

    val newUser = new User
    newUser.userName = "dung"
    newUser.passWord = "dung"

    mongo.save(newUser)

    val user = UserDao.findByUserName("dung")
    assert(user != null && user.id != null)

    mongo.remove(user)
  }

}
