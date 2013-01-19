package org.linkerz.model

import com.gravity.hbase.schema.{HRow, DeserializedResult, HbaseTable, Schema}
import exception.ColumnNotFoundException
import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * The Class FeedGroupSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 12:02 AM
 *
 */
object FeedGroupSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  class FeedGroupTable extends HbaseTable[FeedGroupTable, String, FeedGroupRow](tableName = "FeedGroupTable", rowKeyClass = classOf[String]) {

    def rowBuilder(result: DeserializedResult) = new FeedGroupRow(this, result)

    val info = family[String, String, Any]("info")
    val name = column(info, "name", classOf[String])
  }

  class FeedGroupRow(table: FeedGroupTable, result: DeserializedResult) extends HRow[FeedGroupTable, String](result, table) {
    def toFeedGroupTable = FeedGroup(
      id = rowid,
      name = column(_.name).getOrElse(throw new ColumnNotFoundException(tableName, table.name.getQualifier))
    )
  }

  val FeedGroupTable = table(new FeedGroupTable)

}
