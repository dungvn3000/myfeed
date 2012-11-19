/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.model

import org.scalatest.FunSuite
import org.linkerz.model.{User, UserDao}
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class UserDaoSuite.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class UserDaoSuite extends FunSuite {

  test("test find user by user name") {
    UserDao.save(User(
      userName = "test user1",
      passWord = "test user1"
    ))

    val user = UserDao.findOne(MongoDBObject("userName" -> "test user1"))

    assert(user.isDefined)
  }

  test("insert mulite users") {
    val user1 = User(
      userName = "test user1",
      passWord = "test user1"
    )

    val user2 = User(
      userName = "test user1",
      passWord = "test user1"
    )

    val user3 = User(
      userName = "test user1",
      passWord = "test user1"
    )

    UserDao.insert(List(user1, user2, user3))

    assert(!UserDao.find(MongoDBObject("userName" -> "test user1")).isEmpty)

    UserDao.remove(MongoDBObject("userName" -> "test user1"))
  }

}
