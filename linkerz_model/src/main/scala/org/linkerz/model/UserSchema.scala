package org.linkerz.model

import com.gravity.hbase.schema.{DeserializedResult, HbaseTable, HRow, Schema}
import exception.ColumnNotFoundException
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

  class UserTable extends HbaseTable[UserTable, String, UserTableRow](tableName = "UserTable", rowKeyClass = classOf[String]) {

    def rowBuilder(result: DeserializedResult) = new UserTableRow(this, result)

    val info = family[String, String, Any]("info")
    val password = column(info, "password", classOf[String])

    val userFollow = family[String, String, Any]("userFollow")
    val followDomains = column(userFollow, "followDomains", classOf[Seq[String]])
  }

  class UserTableRow(table: UserTable, result: DeserializedResult) extends HRow[UserTable, String](result, table) {
    def toUser = User(
      id = rowid,
      password = column(_.password).getOrElse(throw new ColumnNotFoundException(tableName, table.password.getQualifier)),
      followDomains = column(_.followDomains).getOrElse(Nil)
    )
  }

  val UserTable = table(new UserTable)

}
