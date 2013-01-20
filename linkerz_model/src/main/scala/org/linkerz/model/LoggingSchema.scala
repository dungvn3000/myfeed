package org.linkerz.model

import com.gravity.hbase.schema.{HbaseTable, HRow, DeserializedResult, Schema}
import exception.ColumnNotFoundException
import org.apache.hadoop.hbase.HBaseConfiguration
import org.joda.time.DateTime

/**
 * The Class LoggingSchema.
 *
 * @author Nguyen Duc Dung
 * @since 1/20/13 3:25 PM
 *
 */
object LoggingSchema extends Schema {

  implicit val conf = HBaseConfiguration.create()

  class LoggingTable extends HbaseTable[LoggingTable, String, LoggingTableRow](tableName = "LoggingTable", rowKeyClass = classOf[String]) {
    def rowBuilder(result: DeserializedResult) = new LoggingTableRow(this, result)

    val info = family[String, String, Any]("info")
    val message = column(info, "message", classOf[String])
    val className = column(info, "className", classOf[String])
    val exceptionClass = column(info, "exceptionClass", classOf[String])
    val stackTrace = column(info, "stackTrace", classOf[String])
    val logType = column(info, "logType", classOf[String])
    val category = column(info, "category", classOf[String])
    val url = column(info, "url", classOf[String])
    val createDate = column(info, "createDate", classOf[DateTime])
  }

  class LoggingTableRow(table: LoggingTable, result: DeserializedResult) extends HRow[LoggingTable, String](result, table) {
    def toLogging = Logging(
      id = rowid,
      message = column(_.message).getOrElse(throw new ColumnNotFoundException(tableName, table.message.getQualifier)),
      className = column(_.className).getOrElse(throw new ColumnNotFoundException(tableName, table.className.getQualifier)),
      exceptionClass = column(_.exceptionClass),
      stackTrace = column(_.stackTrace),
      logType = column(_.logType).getOrElse(throw new ColumnNotFoundException(tableName, table.logType.getQualifier)),
      category = column(_.category).getOrElse(throw new ColumnNotFoundException(tableName, table.category.getQualifier)),
      url = column(_.url),
      createDate = column(_.createDate).getOrElse(throw new ColumnNotFoundException(tableName, table.createDate.getQualifier))
    )
  }

  val LoggingTable = table(new LoggingTable)

  /**
   * Convenient method, to same an entity to database
   * @param logging
   */
  def save(logging: Logging) {
    val puts = LoggingTable.put(logging.id)
      .value(_.message, logging.message)
      .value(_.className, logging.className)
      .value(_.logType, logging.logType)
      .value(_.category, logging.category)
      .value(_.createDate, logging.createDate)

    logging.exceptionClass.map(puts.value(_.exceptionClass, _))
    logging.stackTrace.map(puts.value(_.stackTrace, _))
    logging.url.map(puts.value(_.url, _))

    puts.execute()
  }
}
