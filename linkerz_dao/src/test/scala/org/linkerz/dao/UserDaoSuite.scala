/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.dao

import org.scalatest.FunSuite
import org.linkerz.model.User
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
      username = "test user1",
      password = "test user1",
      email = "testuser1@vketnoi.com"
    ))

    val user = UserDao.findOne(MongoDBObject("userName" -> "test user1"))

    assert(user.isDefined)
  }

  test("insert mulite users") {
    val user1 = User(
      username = "test user1",
      password = "test user1",
      email = "testuser1@vketnoi.com"
    )

    val user2 = User(
      username = "test user1",
      password = "test user1",
      email = "testuser1@vketnoi.com"
    )

    val user3 = User(
      username = "test user1",
      password = "test user1",
      email = "testuser1@vketnoi.com"
    )

    UserDao.insert(List(user1, user2, user3))

    assert(!UserDao.find(MongoDBObject("userName" -> "test user1")).isEmpty)

    UserDao.remove(MongoDBObject("userName" -> "test user1"))
  }

}
