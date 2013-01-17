package org.linkerz.model

import com.gravity.hbase.schema.{DeserializedResult, HbaseTable, HRow, Schema}
import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * The Class UserSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/17/13 5:08 AM
 *
 */
object UserSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  class UserTable extends HbaseTable[UserTable, String, UserTableRow](tableName = "usertable", rowKeyClass = classOf[String]) {

    def rowBuilder(result: DeserializedResult) = new UserTableRow(this, result)

    val info = family[String, String, Any]("info")
    val password = column(info, "password", classOf[String])

    val userFollow = family[String, String, Any]("userfollow")
    val domain = column(userFollow, "domain", classOf[String])
  }

  class UserTableRow(table: UserTable, result: DeserializedResult) extends HRow[UserTable, String](result, table)

}
