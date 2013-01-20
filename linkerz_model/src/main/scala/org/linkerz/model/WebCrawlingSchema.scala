package org.linkerz.model

import com.gravity.hbase.schema._
import exception.ColumnNotFoundException
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

  class WebTable extends HbaseTable[WebTable, String, WebTableRow](tableName = "WebTable", rowKeyClass = classOf[String]) {
    def rowBuilder(result: DeserializedResult) = new WebTableRow(this, result)

    //Metadata Family
    val info = family[String, String, Any]("info")
    val crawledDate = column(info, "crawledDate", classOf[DateTime])
    val domain = column(info, "domain", classOf[String])
    val contentEncoding = column(info, "contentEncoding", classOf[String])
    val score = column(info, "score", classOf[Double])

    //Content Family
    val content = family[String, String, Any]("content")
    val title = column(content, "title", classOf[String])
    val text = column(content, "text", classOf[String])
    val description = column(content, "description", classOf[String])
    val featureImage = column(content, "featureImage", classOf[Array[Byte]])
  }

  class WebTableRow(table: WebTable, result: DeserializedResult) extends HRow[WebTable, String](result, table) {
    def toWebPage = WebPage(
      id = rowid,
      crawledDate = column(_.crawledDate).getOrElse(throw new ColumnNotFoundException(tableName, table.crawledDate.getQualifier)),
      domain = column(_.domain).getOrElse(throw new ColumnNotFoundException(tableName, table.domain.getQualifier)),
      contentEncoding = column(_.contentEncoding),
      score = column(_.score),
      title = column(_.title),
      text = column(_.text),
      description = column(_.description),
      featureImage = column(_.featureImage)
    )
  }

  val WebTable = table(new WebTable)

  /**
   * Convenient method, to same an entity to database
   * @param webPage
   */
  def save(webPage: WebPage) {
    val puts = WebTable.put(webPage.id)
      .value(_.crawledDate, webPage.crawledDate)
      .value(_.domain, webPage.domain)

    webPage.contentEncoding.map(puts.value(_.contentEncoding, _))
    webPage.score.map(puts.value(_.score, _))
    webPage.title.map(puts.value(_.title, _))
    webPage.text.map(puts.value(_.text, _))
    webPage.description.map(puts.value(_.description, _))
    webPage.featureImage.map(puts.value(_.featureImage, _))

    puts.execute()
  }

}
