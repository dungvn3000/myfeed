package org.linkerz.model

import com.gravity.hbase.schema._
import org.apache.hadoop.hbase.HBaseConfiguration
import org.joda.time.DateTime
import com.gravity.hbase.schema.DeserializedResult
import com.gravity.hbase.AnyNotSupportedException

/**
 * The Class WebCrawlingSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/17/13 3:54 AM
 *
 */
object WebCrawlingSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  implicit object ByteArrayConverter extends ByteConverter[Array[Byte]] {
    def toBytes(t: Array[Byte]) = t
    def fromBytes(bytes: Array[Byte], offset: Int, length: Int) = bytes
  }

  class WebTable extends HbaseTable[WebTable, String, WebTableRow](tableName = "webtable", rowKeyClass = classOf[String]) {

    def rowBuilder(result: DeserializedResult) = new WebTableRow(this, result)

    //Metadata Family
    val metadata = family[String, String, Any]("metadata")
    val crawledDate = column(metadata, "crawledDate", classOf[DateTime])
    val domain = column(metadata, "domain", classOf[String])
    val contentEncoding = column(metadata, "contentEncoding", classOf[String])

    //Content Family
    val content = family[String, String, Any]("content", compressed = true)
    val title = column(content, "title", classOf[String])
    val text = column(content, "text", classOf[String])
    val description = column(content, "description", classOf[String])
    val featureImage = column(content, "featureImage", classOf[Array[Byte]])
  }

  class WebTableRow(table: WebTable, result: DeserializedResult) extends HRow[WebTable, String](result, table) {
    def toWebPage = WebPage(
      id = rowid,
      crawledDate = column(_.crawledDate).getOrElse(throw new Exception("WebPage has no crawledDate column")),
      domain = column(_.domain).getOrElse(throw new Exception("WebPage has no domain column")),
      contentEncoding = column(_.contentEncoding),
      title = column(_.title),
      text = column(_.text),
      description = column(_.description),
      featureImage = column(_.featureImage)
    )
  }

  val WebTable = table(new WebTable)

}
