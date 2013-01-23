package org.linkerz.model

import org.junit.Test

/**
 * The Class TableSchemaTest.
 *
 * @author Nguyen Duc Dung
 * @since 1/21/13 1:43 AM
 *
 */
class TableSchemaTest {

  @Test
  def testCreateTable() {
    UserSchema.createTable()
    FeedSchema.createTable()
    LoggingSchema.createTable()
    NewsBoxSchema.createTable()
    WebCrawlingSchema.createTable()
  }

  @Test
  def testDropTable() {
    UserSchema.dropTable()
  }
}
