/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.model

import org.scalatest.FunSuite
import org.linkerz.model.User
import org.linkerz.dao.MongoTemplate
import MongoTemplate._
import org.junit.Assert

/**
 * The Class UserDaoSuite.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class UserDaoSuite extends FunSuite {

  test("test find user by user name") {
    val user = new User
    user.userName = "testUser"
    user.passWord = "testUSer"

    mongo.save(user)

    Assert.assertEquals(true, user.id != null)

    mongo.remove(user)
  }

}
