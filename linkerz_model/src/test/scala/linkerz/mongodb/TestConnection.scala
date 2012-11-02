/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package linkerz.mongodb

import com.mongodb.Mongo
import collection.JavaConversions._
import org.scalatest.FunSuite

/**
 * The Class TestConnection.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class TestConnection extends FunSuite {

  test("test mongo db connection") {
    val mongo = new Mongo("127.0.0.1")
    mongo.getDatabaseNames.foreach(println)
  }

}
