/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package linkerz.mongodb

import com.mongodb.Mongo
import org.junit.Test
import collection.JavaConversions._
/**
 * The Class TestConnection.
 *
 * @author Nguyen Duc Dung
 * @since 9/1/12 8:27 PM
 *
 */
class TestConnection {

  @Test
  def testConnection() {
    val mongo = new Mongo("127.0.0.1")
    mongo.getDatabaseNames.foreach(println)
  }

}
