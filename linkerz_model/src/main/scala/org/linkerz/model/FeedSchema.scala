package org.linkerz.model

import com.gravity.hbase.schema.{HbaseTable, HRow, DeserializedResult, Schema}
import exception.{KeyNotFoundException, ColumnNotFoundException}
import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * The Class FeedSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 1:10 AM
 *
 */
object FeedSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  class FeedTable extends HbaseTable[FeedTable, String, FeedTableRow](tableName = "FeedTable", rowKeyClass = classOf[String]) {

    def rowBuilder(result: DeserializedResult) = new FeedTableRow(this, result)

    val info = family[String, String, Any]("info")
    val name = column(info, "name", classOf[String])
    val groupName = column(info, "groupName", classOf[String])
    val enable = column(info, "enable", classOf[Boolean])
    val urlRegex = column(info, "urlRegex", classOf[String])
    val excludeUrl = column(info, "excludeUrl", classOf[Seq[String]])
    val contentSelection = column(info, "contentSelection", classOf[String])
    val removeSelections = column(info, "removeSelections", classOf[Seq[String]])

  }

  class FeedTableRow(table: FeedTable, result: DeserializedResult) extends HRow[FeedTable, String](result, table) {
    def toFeed = Feed(
      id = rowid,
      groupName = column(_.groupName).getOrElse(throw new ColumnNotFoundException(tableName, table.groupName.getQualifier)),
      name = column(_.name).getOrElse(throw new ColumnNotFoundException(tableName, table.name.getQualifier)),
      enable = column(_.enable).getOrElse(throw new ColumnNotFoundException(tableName, table.enable.getQualifier)),
      urlRegex = column(_.urlRegex).getOrElse(throw new ColumnNotFoundException(tableName, table.urlRegex.getQualifier)),
      excludeUrl = column(_.excludeUrl).getOrElse(Nil),
      contentSelection = column(_.contentSelection).getOrElse(throw new ColumnNotFoundException(tableName, table.contentSelection.getQualifier)),
      removeSelections = column(_.removeSelections).getOrElse(Nil)
    )
  }

  val FeedTable = table(new FeedTable)

  def save(feed: Feed) {
    FeedTable.put(feed.id)
      .value(_.name, feed.name)
      .value(_.groupName, feed.groupName)
      .value(_.enable, feed.enable)
      .value(_.urlRegex, feed.urlRegex)
      .value(_.excludeUrl, feed.excludeUrl)
      .value(_.contentSelection, feed.contentSelection)
      .value(_.removeSelections, feed.removeSelections)
      .execute()
  }

}
