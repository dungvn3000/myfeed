/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.mongodb

import org.scalatest.FunSuite
import com.mongodb.casbah.MongoConnection
import org.linkerz.model.{User, UserDao}
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class DaoSuite.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class DaoSuite extends FunSuite {

  test("insert user") {
    UserDao.save(User(
      userName = "dung", passWord = "dung"
    ))

    val user = UserDao.findOne(MongoDBObject("userName" -> "dung"))

    assert(user.isDefined && user.get._id != null)

    UserDao.remove(user.get)
  }

}
