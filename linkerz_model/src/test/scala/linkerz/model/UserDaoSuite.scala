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
      userName = "dung",
      passWord = "dung"
    ))

    val user = UserDao.findOne(MongoDBObject("userName" -> "dung"))

    assert(user.isDefined)
  }

}
