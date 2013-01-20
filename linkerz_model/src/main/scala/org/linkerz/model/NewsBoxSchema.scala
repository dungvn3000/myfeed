package org.linkerz.model

import com.gravity.hbase.schema.{HbaseTable, HRow, DeserializedResult, Schema}
import exception.{KeyNotFoundException, ColumnNotFoundException}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.joda.time.DateTime

/**
 * The Class NewsBoxSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 2:26 PM
 *
 */
object NewsBoxSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  class NewsBoxTable extends HbaseTable[NewsBoxTable, String, NewsBoxTableRow](tableName = "NewsBoxTable", rowKeyClass = classOf[String]) {
    def rowBuilder(result: DeserializedResult) = new NewsBoxTableRow(this, result)

    val info = family[String, String, Any]("info")
    val userId = column(info, "userId", classOf[String])
    val webPageId = column(info, "webPageId", classOf[String])
    val feedGroupId = column(info, "feedGroupId", classOf[String])
    val clicked = column(info, "clicked", classOf[Boolean])
    val createdDate = column(info, "createdDate", classOf[DateTime])
  }

  class NewsBoxTableRow(table: NewsBoxTable, result: DeserializedResult) extends HRow[NewsBoxTable, String](result, table) {
    def toNewsBox = NewsBox(
      id = rowid,
      userId = column(_.userId).getOrElse(throw new ColumnNotFoundException(tableName, table.userId.getQualifier)),
      webPageId = column(_.webPageId).getOrElse(throw new ColumnNotFoundException(tableName, table.webPageId.getQualifier)),
      feedGroupId = column(_.feedGroupId).getOrElse(throw new ColumnNotFoundException(tableName, table.feedGroupId.getQualifier)),
      clicked = column(_.clicked).getOrElse(throw new ColumnNotFoundException(tableName, table.clicked.getQualifier)),
      createdDate = column(_.createdDate).getOrElse(throw new ColumnNotFoundException(tableName, table.createdDate.getQualifier))
    )
  }

  val NewsBoxTable = table(new NewsBoxTable)
}
