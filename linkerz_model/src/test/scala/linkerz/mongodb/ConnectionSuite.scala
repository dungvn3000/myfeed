/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.mongodb

import org.scalatest.FunSuite
import com.mongodb.casbah.MongoConnection
import org.linkerz.mongodb.model.{UserDao, User}

/**
 * The Class ConnectionSuite.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class ConnectionSuite extends FunSuite {

  test("test mongo db connection") {
    val connection = MongoConnection()
    connection.databaseNames.foreach(println)

    val _id = UserDao.insert(User(userName = "dung", passWord = "dung"))

    println(_id)
  }

}
