package vn.myfeed.logger

import org.scalatest.FunSuite
import com.mongodb.casbah.commons.MongoDBObject
import org.junit.Assert
import vn.myfeed.dao.LoggingDao
import vn.myfeed.model.LogCategory

/**
 * The Class DBLoggerSuite.
 *
 * @author Nguyen Duc Dung
 * @since 11/19/12 10:12 AM
 *
 */
class DBLoggerSuite extends FunSuite with DBLogger {

  test("store an error into the database") {
    storeError("test error1", LogCategory.System)
    storeError("test error1", "example.com", new Exception, LogCategory.Crawling)

    Assert.assertEquals(false, LoggingDao.find(MongoDBObject("message" -> "test error1")).isEmpty)
    LoggingDao.remove(MongoDBObject("message" -> "test error1"))
  }

}
